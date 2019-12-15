package io.github.alloffabric.beeproductive.hive;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BeehiveProvider {
	Beehive getBeehive(World world, BlockPos pos, BlockState state);
}
