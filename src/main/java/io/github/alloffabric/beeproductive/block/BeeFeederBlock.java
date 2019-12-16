package io.github.alloffabric.beeproductive.block;

import io.github.alloffabric.beeproductive.api.Nectar;
import io.github.alloffabric.beeproductive.block.entity.BeeFeederBlockEntity;
import io.github.alloffabric.beeproductive.init.BeeProdNectars;
import io.github.alloffabric.beeproductive.item.NectarItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BeeFeederBlock extends Block implements InventoryProvider, BlockEntityProvider {
	public BeeFeederBlock(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) return ActionResult.SUCCESS;
		BlockEntity be = world.getBlockEntity(pos);
		if (be instanceof Inventory) {
			Inventory in = (Inventory)be;
			ItemStack stack = player.getStackInHand(hand);
			if (stack.getItem() instanceof NectarItem || stack.isEmpty()) {
				ItemStack invStack = in.getInvStack(0);
				in.setInvStack(0, stack);
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
			ItemStack stack = in.getInvStack(0);
			if (stack.getItem() instanceof NectarItem) {
				Nectar nectar = ((NectarItem)stack.getItem()).getNectar();
				stack.decrement(1);
				return nectar;
			}
		}
		return BeeProdNectars.EMPTY;
	}

	@Override
	public SidedInventory getInventory(BlockState state, IWorld world, BlockPos pos) {
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
