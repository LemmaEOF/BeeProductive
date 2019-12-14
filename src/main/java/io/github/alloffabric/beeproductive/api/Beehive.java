package io.github.alloffabric.beeproductive.api;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface Beehive {
	int getHoneyLevel(World world, BlockPos pos, BlockState state);

	void setHoneyLevel(World world, BlockPos pos, BlockState state, int level);

	void addHoneyFlavor(World world, BlockPos pos, BlockState state, HoneyFlavor flavor);

	Object2IntMap<HoneyFlavor> getFlavors(World world, BlockPos pos, BlockState state);

	void harvestHoney(World world, BlockPos pos, BlockState state, HoneyFlavor harvested);

	//TODO: what else should beehives provide?
}
