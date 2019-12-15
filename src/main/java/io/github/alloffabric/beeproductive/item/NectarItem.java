package io.github.alloffabric.beeproductive.item;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.BeeComponent;
import io.github.alloffabric.beeproductive.api.Nectar;
import io.github.alloffabric.beeproductive.hooks.BeeEntityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.HashMap;
import java.util.Map;

public class NectarItem extends Item {
	public static final Map<Nectar, Item> NECTAR_MAP = new HashMap<>();

	protected final Nectar nectar;

	public NectarItem(Nectar nectar, Settings settings) {
		super(settings);
		this.nectar = nectar;
		NECTAR_MAP.put(nectar, this);
	}

	@Override
	public boolean useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (entity instanceof BeeEntity) {
			BeeEntity bee = (BeeEntity)entity;
			BeeComponent component = BeeProductive.BEE_COMPONENT.get(bee);
			component.setNectar(nectar);
			((BeeEntityAccessor)bee).beeproductive$setNectar(true);
			return true;
		}
		return false;
	}

	public Nectar getNectar() {
		return nectar;
	}
}
