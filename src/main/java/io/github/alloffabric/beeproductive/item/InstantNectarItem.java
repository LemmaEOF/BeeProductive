package io.github.alloffabric.beeproductive.item;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.Nectar;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Hand;

public class InstantNectarItem extends NectarItem {
	public InstantNectarItem(Nectar nectar, Settings settings) {
		super(nectar, settings);
	}

	@Override
	public boolean useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (entity.getEntityWorld().isClient) return false;
		if (entity instanceof BeeEntity) {
			BeeEntity bee = (BeeEntity)entity;
			//TODO: no-op beehive
			nectar.onApply(bee, null);
			stack.decrement(1);
			return true;
		}
		return false;
	}
}
