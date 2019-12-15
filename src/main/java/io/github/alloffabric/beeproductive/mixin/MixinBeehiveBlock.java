package io.github.alloffabric.beeproductive.mixin;

import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import io.github.alloffabric.beeproductive.hive.Beehive;
import io.github.alloffabric.beeproductive.hive.BeehiveProvider;
import io.github.alloffabric.beeproductive.hive.SimpleBeehive;
import io.github.alloffabric.beeproductive.init.BeeHoneys;
import net.minecraft.block.BeeHiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeeHiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(BeeHiveBlock.class)
public abstract class MixinBeehiveBlock extends Block implements BeehiveProvider {
	private ThreadLocal<BlockState> cachedState = new ThreadLocal<>();
	private ThreadLocal<World> cachedWorld = new ThreadLocal<>();
	private ThreadLocal<BlockPos> cachedPos = new ThreadLocal<>();

	@Shadow public abstract BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos);

	@Shadow public abstract List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder);

	public MixinBeehiveBlock(Settings settings) {
		super(settings);
	}

	@Override
	public Beehive getBeehive(World world, BlockPos pos, BlockState state) {
		return new SimpleBeehive(world, pos, state);
	}

	private @Nullable BeeHiveBlockEntity getBE(World world, BlockPos pos) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be instanceof BeeHiveBlockEntity) return (BeeHiveBlockEntity) be;
		return null;
	}

	@Inject(method = "dropHoneycomb", at = @At("HEAD"), cancellable = true)
	private static void dropFlavoredCombs(World world, BlockPos pos, CallbackInfo info) {
		BlockState state = world.getBlockState(pos);
		HoneyFlavor toDrop = getDroppedFlavor(world, pos, state);
		if (toDrop == BeeHoneys.VANILLA) return;
		ItemStack stack = toDrop.getSheared();
		for (int i = 0; i < stack.getCount(); i++) {
			dropStack(world, pos, new ItemStack(stack.getItem()));
		}
		info.cancel();
	}

	@Inject(method = "onUse", at = @At("HEAD"))
	private void cacheThreadLocals(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> info) {
		this.cachedState.set(state);
		this.cachedWorld.set(world);
		this.cachedPos.set(pos);
	}

	@ModifyArg(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setStackInHand(Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;)V"))
	private ItemStack modSetStack(ItemStack original) {
		HoneyFlavor toDrop = getDroppedFlavor(cachedWorld.get(), cachedPos.get(), cachedState.get());
		if (toDrop == BeeHoneys.VANILLA) return original;
		return toDrop.getBottled();
	}

	@ModifyArg(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z"))
	private ItemStack modInsertStack(ItemStack original) {
		HoneyFlavor toDrop = getDroppedFlavor(cachedWorld.get(), cachedPos.get(), cachedState.get());
		if (toDrop == BeeHoneys.VANILLA) return original;
		return toDrop.getBottled();
	}

	@ModifyArg(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;dropItem(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/ItemEntity;"))
	private ItemStack modDropStack(ItemStack original) {
		HoneyFlavor toDrop = getDroppedFlavor(cachedWorld.get(), cachedPos.get(), cachedState.get());
		if (toDrop == BeeHoneys.VANILLA) return original;
		return toDrop.getBottled();
	}

	private static HoneyFlavor getDroppedFlavor(World world, BlockPos pos, BlockState state) {
		Beehive hive = ((BeehiveProvider)state.getBlock()).getBeehive(world, pos, state);
		return hive.getFlavorToHarvest();
	}

}
