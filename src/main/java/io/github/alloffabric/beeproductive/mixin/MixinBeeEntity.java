package io.github.alloffabric.beeproductive.mixin;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.BeeComponent;
import io.github.alloffabric.beeproductive.block.FeederBlock;
import io.github.alloffabric.beeproductive.hooks.BeeEntityAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeeEntity.class)
public abstract class MixinBeeEntity implements BeeEntityAccessor {

	@Shadow protected abstract void setHasNectar(boolean hasNectar);

	@ModifyArg(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;fromTag(Lnet/minecraft/tag/Tag;)Lnet/minecraft/recipe/Ingredient;"))
	private Tag<Item> modTemptTag(Tag<Item> original) {
		return BeeProductive.BEE_TEMPTING;
	}

	@Override
	public void beeproductive$setNectar(boolean hasNectar) {
		this.setHasNectar(hasNectar);
	}

	@ModifyArg(method = "isFlowers", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;matches(Lnet/minecraft/tag/Tag;)Z"))
	private Tag<Block> modFeedTag(Tag<Block> original) {
		return BeeProductive.BEE_FEEDING;
	}

	@Mixin(targets = "net.minecraft.entity.passive.BeeEntity.PollinateGoal")
	public static abstract class MixinPollinateGoal {
		@Shadow @Final private BeeEntity this$0;

		@Inject(method = "stop", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/BeeEntity;setHasNectar(Z)V"))
		private void applyNectar(CallbackInfo info) {
			World world = this$0.getEntityWorld();
			BlockPos pos = this$0.getFlowerPos();
			BeeComponent component = BeeProductive.BEE_COMPONENT.get(this$0);
			if (world.getBlockState(pos).getBlock() instanceof FeederBlock) {
				component.setNectar(((FeederBlock) world.getBlockState(pos).getBlock()).consumeNectar(world, pos));
			}
		}
	}
}
