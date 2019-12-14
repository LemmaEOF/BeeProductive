package io.github.alloffabric.beeproductive.mixin;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.BeeComponent;
import io.github.alloffabric.beeproductive.api.Beehive;
import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import io.github.alloffabric.beeproductive.api.Nectar;
import io.github.alloffabric.beeproductive.impl.BeehiveHoneyFlavorSetter;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeeHiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(value = BeeHiveBlockEntity.class, priority = 2000)
public abstract class MixinBeehiveBlockEntity extends BlockEntity implements BeehiveHoneyFlavorSetter {

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
	public void beeproductive$clearHoneyFlavors() {
		flavors.clear();
	}

	@Redirect(method = "releaseBee", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isNight()Z"))
	private boolean nightProxy(World world, BlockState state, CompoundTag tag, List<Entity> entities, BeeHiveBlockEntity.BeeState beeState) {
		BeeComponent comp = getComponent(tag);
		if (comp == null) return world.isNight();
		if (comp.getTraitValue(BeeProductive.NOCTURNAL)) return false;
		//Bee Angry-Est compat
		else if (tag.contains("nocturnal")) return world.isDay();
		else return world.isNight();
	}

	@Redirect(method = "releaseBee", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isRaining()Z"))
	private boolean rainProxy(World world, BlockState state, CompoundTag tag, List<Entity> entities, BeeHiveBlockEntity.BeeState beeState) {
		BeeComponent comp = getComponent(tag);
		if (comp == null) return world.isRaining();
		if (comp.getTraitValue(BeeProductive.WEATHERPROOF)) return false;
		else return world.isRaining();
	}

	@Inject(method = "releaseBee", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/BeeEntity;onHoneyDelivered()V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void applyNectarEffects(BlockState state, CompoundTag tag, List<Entity> entities, BeeHiveBlockEntity.BeeState beeState, CallbackInfoReturnable<Boolean> info,
									BlockPos pos, Direction facingDir, Entity entity, BeeEntity bee) {
		Beehive hive = (Beehive) world.getBlockState(pos).getBlock();
		BeeComponent component = BeeProductive.BEE_COMPONENT.get(bee);
		component.getNectar().onApply(bee, hive);
		component.setNectar(Nectar.NONE);
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
