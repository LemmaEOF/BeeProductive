package io.github.alloffabric.beeproductive.init;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.block.BeeFeederBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BeeProdBlocks {
	public static final Block BEE_FEEDER = register("bee_feeder", new BeeFeederBlock(FabricBlockSettings.of(Material.WOOD).breakByTool(FabricToolTags.AXES).nonOpaque().build()), BeeProdItems.TOOLS_GROUP);

	public static void init() { }

	public static Block register(String name, Block block, ItemGroup group) {
		Block ret =  Registry.register(Registry.BLOCK, new Identifier(BeeProductive.MODID, name), block);
		Registry.register(Registry.ITEM, new Identifier(BeeProductive.MODID, name), new BlockItem(block, new Item.Settings().group(group)));
		return ret;
	}
}
