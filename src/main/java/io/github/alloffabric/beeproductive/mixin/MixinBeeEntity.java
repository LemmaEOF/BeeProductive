package io.github.alloffabric.beeproductive.mixin;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.BeeComponent;
import io.github.alloffabric.beeproductive.block.BeeFeederBlock;
import io.github.alloffabric.beeproductive.hooks.BeeEntityAccessor;
import io.github.alloffabric.beeproductive.init.BeeProdTags;
import io.github.alloffabric.beeproductive.init.BeeProdTraits;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.Item;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.function.Predicate;

@Mixin(BeeEntity.class)
public abstract class MixinBeeEntity implements BeeEntityAccessor {

	@Shadow protected abstract void setHasNectar(boolean hasNectar);

	@ModifyArg(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;fromTag(Lnet/minecraft/tag/Tag;)Lnet/minecraft/recipe/Ingredient;"))
	private Tag<Item> modTemptTag(Tag<Item> original) {
		return BeeProdTags.BEE_TEMPTING;
	}

	@Override
	public void beeproductive$setNectar(boolean hasNectar) {
		this.setHasNectar(hasNectar);
	}

	@ModifyArg(method = "isFlowers", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;matches(Lnet/minecraft/tag/Tag;)Z"))
	private Tag<Block> modFeedTag(Tag<Block> original) {
		return BeeProdTags.BEE_FEEDING;
	}

	@Inject(method = "isAngry", at = @At("HEAD"))
	private void modAnger(CallbackInfoReturnable<Boolean> info) {
		BeeComponent component = BeeProductive.BEE_COMPONENT.get(this);
		if (component.getTraitValue(BeeProdTraits.PACIFIST)) info.setReturnValue(false);
	}

	@Mixin(targets = "net.minecraft.entity.passive.BeeEntity$PollinateGoal")
	public static abstract class MixinPollinateGoal {
		@Shadow @Final private BeeEntity field_20377;

		@Shadow @Final @Mutable
		private Predicate<BlockState> flowerPredicate = (state) -> {
			if (state.matches(BlockTags.TALL_FLOWERS)) {
				if (state.getBlock() == Blocks.SUNFLOWER) {
					return state.get(TallPlantBlock.HALF) == DoubleBlockHalf.UPPER;
				} else {
					return true;
				}
			} else {
				return state.matches(BeeProdTags.BEE_FEEDING);
			}
		};

		@Shadow protected abstract boolean completedPollination();

		@Inject(method = "Lnet/minecraft/entity/passive/BeeEntity$PollinateGoal;stop()V", at = @At("HEAD"))
		private void applyNectar(CallbackInfo info) {
			World world = field_20377.getEntityWorld();
			BlockPos pos = field_20377.getFlowerPos();
			BeeComponent component = BeeProductive.BEE_COMPONENT.get(field_20377);
			if (this.completedPollination() && pos != null) {
				if (world.getBlockState(pos).getBlock() instanceof BeeFeederBlock) {
					component.setNectar(((BeeFeederBlock) world.getBlockState(pos).getBlock()).consumeNectar(world, pos));
				}
			}
		}
	}
}
