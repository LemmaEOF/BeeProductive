package io.github.alloffabric.beeproductive.init;

import io.github.alloffabric.beeproductive.BeeProductive;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class BeeTags {
	public static final Tag<Item> BEE_TEMPTING = TagRegistry.item(new Identifier(BeeProductive.MODID, "bee_tempting"));
	public static final Tag<Block> BEE_FEEDING = TagRegistry.block(new Identifier(BeeProductive.MODID, "bee_feeding"));

	public static void init() { }
}
