package io.github.alloffabric.beeproductive.block;

import io.github.alloffabric.beeproductive.api.Nectar;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FeederBlock extends Block {
	public FeederBlock(Settings settings) {
		super(settings);
	}

	public Nectar consumeNectar(World world, BlockPos pos) {
		//TODO: impl
		return Nectar.NONE;
	}
}
