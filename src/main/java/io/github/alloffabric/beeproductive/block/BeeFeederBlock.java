package io.github.alloffabric.beeproductive.block;

import io.github.alloffabric.beeproductive.api.Nectar;
import io.github.alloffabric.beeproductive.init.BeeNectars;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BeeFeederBlock extends Block {
	public BeeFeederBlock(Settings settings) {
		super(settings);
	}

	public Nectar consumeNectar(World world, BlockPos pos) {
		//TODO: impl
		return BeeNectars.EMPTY;
	}
}
