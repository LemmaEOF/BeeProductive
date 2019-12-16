package io.github.alloffabric.beeproductive.init;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BeeProdHoneys {

	public static final HoneyFlavor VANILLA = register("vanilla", new HoneyFlavor(new ItemStack(Items.HONEYCOMB, 3), new ItemStack(Items.HONEY_BOTTLE)));
	public static final HoneyFlavor ENDER = register("ender", new HoneyFlavor(new ItemStack(Items.ENDER_PEARL, 2), new ItemStack(Items.DRAGON_BREATH)));

	public static void init() { }

	private static HoneyFlavor register(String name, HoneyFlavor flavor) {
		return Registry.register(BeeProductive.HONEY_FLAVORS, new Identifier(BeeProductive.MODID, name), flavor);
	}
}
