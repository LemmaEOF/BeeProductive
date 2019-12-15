package io.github.alloffabric.beeproductive.init;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.item.NectarItem;
import io.github.alloffabric.beeproductive.item.BeeSwabItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BeeItems {

	public static final Item NOCTURNAL_NECTAR = register("nocturnal_nectar", new NectarItem(BeeNectars.NOCTURNAL, new Item.Settings().group(ItemGroup.MISC)));
	public static final Item WEATHERPROOF_NECTAR = register("weatherproof_nectar", new NectarItem(BeeNectars.WEATHERPROOF, new Item.Settings().group(ItemGroup.MISC)));
	public static final Item TRANS_NECTAR = register("trans_skin_nectar", new NectarItem(BeeNectars.TRANS_SKIN, new Item.Settings().group(ItemGroup.MISC)));
	public static final Item NONBINARY_NECTAR = register("nonbinary_skin_nectar", new NectarItem(BeeNectars.NONBINARY_SKIN, new Item.Settings().group(ItemGroup.MISC)));
	public static final Item ENDER_NECTAR = register("ender_nectar", new NectarItem(BeeNectars.ENDER, new Item.Settings().group(ItemGroup.MISC)));

	public static final Item BEE_SWAB = register("bee_swab", new BeeSwabItem(new Item.Settings().group(ItemGroup.MISC)));

	public static void init() { }

	private static Item register(String name, Item item) {
		return Registry.register(Registry.ITEM, new Identifier(BeeProductive.MODID, name), item);
	}
}
