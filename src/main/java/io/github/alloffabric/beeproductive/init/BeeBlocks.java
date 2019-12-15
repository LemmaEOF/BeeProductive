package io.github.alloffabric.beeproductive.init;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.block.FeederBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BeeBlocks {
	public static final Block BEE_FEEDER = register("bee_feeder", new FeederBlock(FabricBlockSettings.of(Material.WOOD).breakByTool(FabricToolTags.AXES).nonOpaque().build()));

	public static void init() { }

	public static Block register(String name, Block block) {
		return Registry.register(Registry.BLOCK, new Identifier(BeeProductive.MODID, name), block);
	}
}
