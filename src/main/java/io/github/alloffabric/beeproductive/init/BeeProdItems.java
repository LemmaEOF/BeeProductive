package io.github.alloffabric.beeproductive.init;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.item.NectarItem;
import io.github.alloffabric.beeproductive.item.BeeSwabItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BeeProdItems {

	//I really wish there was a better way to set item group display stacks...
	public static final ItemGroup TOOLS_GROUP = FabricItemGroupBuilder.build(new Identifier(BeeProductive.MODID, "tools"), () -> new ItemStack(Registry.ITEM.get(new Identifier(BeeProductive.MODID, "bee_swab"))));
	public static final ItemGroup NECTAR_GROUP = FabricItemGroupBuilder.build(new Identifier(BeeProductive.MODID, "nectars"), () -> new ItemStack(Registry.ITEM.get(new Identifier(BeeProductive.MODID, "trans_skin_nectar"))));

	public static final Item NOCTURNAL_NECTAR = register("nocturnal_nectar", new NectarItem(BeeProdNectars.NOCTURNAL, new Item.Settings().group(NECTAR_GROUP)));
	public static final Item WEATHERPROOF_NECTAR = register("weatherproof_nectar", new NectarItem(BeeProdNectars.WEATHERPROOF, new Item.Settings().group(NECTAR_GROUP)));
	public static final Item PACIFIST_NECTAR = register("pacifist_nectar", new NectarItem(BeeProdNectars.PACIFIST, new Item.Settings().group(NECTAR_GROUP)));
	public static final Item TRANS_NECTAR = register("trans_skin_nectar", new NectarItem(BeeProdNectars.TRANS_SKIN, new Item.Settings().group(NECTAR_GROUP)));
	public static final Item NONBINARY_NECTAR = register("nonbinary_skin_nectar", new NectarItem(BeeProdNectars.NONBINARY_SKIN, new Item.Settings().group(NECTAR_GROUP)));
	public static final Item ENDER_NECTAR = register("ender_nectar", new NectarItem(BeeProdNectars.ENDER, new Item.Settings().group(NECTAR_GROUP)));

	public static final Item BEE_SWAB = register("bee_swab", new BeeSwabItem(new Item.Settings().group(TOOLS_GROUP)));

	public static void init() { }

	private static Item register(String name, Item item) {
		return Registry.register(Registry.ITEM, new Identifier(BeeProductive.MODID, name), item);
	}

	static { }
}
