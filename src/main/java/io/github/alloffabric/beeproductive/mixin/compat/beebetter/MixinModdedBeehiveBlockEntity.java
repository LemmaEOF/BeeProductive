package io.github.alloffabric.beeproductive.mixin.compat.beebetter;

import com.github.draylar.beebetter.entity.ModdedBeehiveBlockEntity;
import com.github.draylar.beebetter.util.BeeState;
import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.BeeComponent;
import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import io.github.alloffabric.beeproductive.api.hive.Beehive;
import io.github.alloffabric.beeproductive.api.hive.BeehiveProvider;
import io.github.alloffabric.beeproductive.hooks.BeehiveAccessor;
import io.github.alloffabric.beeproductive.init.BeeProdNectars;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Optional;

@Mixin(value = ModdedBeehiveBlockEntity.class)
public abstract class MixinModdedBeehiveBlockEntity extends BlockEntity implements BeehiveAccessor {

	Object2IntMap<HoneyFlavor> flavors = new Object2IntOpenHashMap<>();

	public MixinModdedBeehiveBlockEntity(BlockEntityType<?> type) {
		super(type);
	}

	@Override
	public Object2IntMap<HoneyFlavor> beeproductive$getHoneyFlavors() {
		return flavors;
	}

	@Override
	public void beeproductive$addHoneyFlavor(HoneyFlavor flavor) {
		int flavorVal = flavors.getOrDefault(flavor, 0) + 1;
		flavors.put(flavor, flavorVal);
	}

	@Override
	public void beeproductive$harvestHoneyFlavor(HoneyFlavor flavor) {
		int amount = flavors.getInt(flavor);
		if (amount > 5) {
			flavors.put(flavor, amount - 5);
		} else {
			int left = 5 - amount;
			flavors.removeInt(flavor);
			for (HoneyFlavor newFlav : flavors.keySet()) {
				int newAmount = flavors.getInt(newFlav);
				if (newAmount >= left) {
					flavors.put(newFlav, newAmount - left);
					break;
				} else {
					left = left - newAmount;
					flavors.removeInt(newFlav);
				}
			}
		}
	}

	@Inject(method = "releaseBee", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/BeeEntity;onHoneyDelivered()V"), remap = false)
	private void applyNectarEffects(BlockState state, CompoundTag tag, List<Entity> entities, BeeState beeState, CallbackInfoReturnable<Boolean> info) {
		Beehive hive = ((BeehiveProvider) world.getBlockState(pos).getBlock()).getBeehive(this.world, pos, state);
		Optional<Entity> entity = EntityType.getEntityFromTag(tag, world);
		if (entity.isPresent() && entity.get() instanceof BeeEntity) {
			BeeEntity bee = (BeeEntity)entity.get();
			BeeComponent component = BeeProductive.BEE_COMPONENT.get(bee);
			component.getNectar().onApply(bee, hive);
			component.setNectar(BeeProdNectars.EMPTY);
		}
	}

	@Inject(method = "toTag", at = @At("RETURN"))
	private void writeFlavorTags(CompoundTag tag, CallbackInfoReturnable<CompoundTag> info) {
		CompoundTag beeProductiveTag = new CompoundTag();
		CompoundTag flavorTag = new CompoundTag();
		for (HoneyFlavor flavor : flavors.keySet()) {
			flavorTag.putInt(BeeProductive.HONEY_FLAVORS.getId(flavor).toString(), flavors.getInt(flavor));
		}
		beeProductiveTag.put("Flavors", flavorTag);
		tag.put("BeeProductive", beeProductiveTag);
	}

	@Inject(method = "fromTag", at = @At("RETURN"))
	private void readFlavorTags(CompoundTag tag, CallbackInfo info) {
		flavors.clear();
		CompoundTag beeProductiveTag = tag.getCompound("BeeProductive");
		CompoundTag flavorTag = beeProductiveTag.getCompound("Flavors");
		for (String key : flavorTag.getKeys()) {
			flavors.put(BeeProductive.HONEY_FLAVORS.get(new Identifier(key)), flavorTag.getInt(key));
		}
	}

	private BeeComponent getComponent(CompoundTag tag) {
		Entity entity = EntityType.loadEntityWithPassengers(tag, this.world, e -> e);
		if (entity instanceof BeeEntity) {
			return BeeProductive.BEE_COMPONENT.get(entity);
		}
		return null;
	}
}
