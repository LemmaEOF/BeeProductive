package io.github.alloffabric.beeproductive.mixin;

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
import net.minecraft.block.entity.BeehiveBlockEntity;
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
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(value = BeehiveBlockEntity.class, priority = 2000)
public abstract class MixinBeehiveBlockEntity extends BlockEntity implements BeehiveAccessor {

	Object2IntMap<HoneyFlavor> flavors = new Object2IntOpenHashMap<>();

	public MixinBeehiveBlockEntity(BlockEntityType<?> type) {
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
		flavors.clear();
	}

	@Inject(method = "releaseBee", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/BeeEntity;onHoneyDelivered()V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void applyNectarEffects(BlockState state, @Coerce Object tag, List<Entity> entities, BeehiveBlockEntity.BeeState beeState, CallbackInfoReturnable<Boolean> info,
									BlockPos pos, CompoundTag t, Direction facingDir, boolean unk, Entity entity, BeeEntity bee) {
		Beehive hive = ((BeehiveProvider) world.getBlockState(pos).getBlock()).getBeehive(this.world, pos, state);
		BeeComponent component = BeeProductive.BEE_COMPONENT.get(bee);
		component.getNectar().onApply(bee, hive);
		component.setNectar(BeeProdNectars.EMPTY);
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
	private void readFlavorTags(BlockState state, CompoundTag tag, CallbackInfo info) {
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
