package io.github.alloffabric.beeproductive.block;

import io.github.alloffabric.beeproductive.api.Nectar;
import io.github.alloffabric.beeproductive.block.entity.BeeFeederBlockEntity;
import io.github.alloffabric.beeproductive.init.BeeProdNectars;
import io.github.alloffabric.beeproductive.item.NectarItem;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import javax.annotation.Nullable;

public class BeeFeederBlock extends Block implements InventoryProvider, BlockEntityProvider {
	public BeeFeederBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		//TODO: change once Blockbench's VoxelShape export is fixed
		return VoxelShapes.cuboid(5/16d, 0, 5/16d, 11/16d, 6/16d, 11/16d);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return !world.getBlockState(pos.down()).isAir();
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return !canPlaceAt(world.getBlockState(pos), world, pos)? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		SidedInventory inv = getInventory(state, world, pos);
		if (inv != null) ItemScatterer.spawn(world, pos, inv);
		super.onStateReplaced(state, world, pos, newState, moved);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) return ActionResult.SUCCESS;
		BlockEntity be = world.getBlockEntity(pos);
		if (be instanceof Inventory) {
			Inventory in = (Inventory)be;
			ItemStack stack = player.getStackInHand(hand);
			if (stack.getItem() instanceof NectarItem || stack.isEmpty()) {
				ItemStack invStack = in.getStack(0);
				in.setStack(0, stack);
				player.setStackInHand(hand, invStack);
				player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1F, 1F);
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.SUCCESS;
	}

	public Nectar consumeNectar(World world, BlockPos pos) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be instanceof Inventory) {
			Inventory in = (Inventory)be;
			ItemStack stack = in.getStack(0);
			if (stack.getItem() instanceof NectarItem) {
				Nectar nectar = ((NectarItem)stack.getItem()).getNectar();
				stack.decrement(1);
				return nectar;
			}
		}
		return BeeProdNectars.EMPTY;
	}

	@Override
	public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be instanceof SidedInventory) {
			return (SidedInventory)be;
		}
		return null;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView view) {
		return new BeeFeederBlockEntity();
	}
}
