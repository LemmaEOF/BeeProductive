package io.github.alloffabric.beeproductive.item;

import io.github.alloffabric.beeproductive.api.Nectar;
import io.github.alloffabric.beeproductive.impl.DummyHive;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.HashMap;
import java.util.Map;

public class InstantNectarItem extends Item {
	public static final Map<Nectar, Item> INSTANT_NECTAR_MAP = new HashMap<>();

	protected final Nectar nectar;

	public InstantNectarItem(Nectar nectar, Settings settings) {
		super(settings);
		this.nectar = nectar;
		INSTANT_NECTAR_MAP.put(nectar, this);
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (entity.getEntityWorld().isClient) return ActionResult.PASS;
		if (entity instanceof BeeEntity) {
			BeeEntity bee = (BeeEntity)entity;
			nectar.onApply(bee, DummyHive.INSTANCE);
			stack.decrement(1);
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	public Nectar getNectar() {
		return nectar;
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return true;
	}
}
