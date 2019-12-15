package io.github.alloffabric.beeproductive.impl;

import io.github.alloffabric.beeproductive.hive.Beehive;
import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//no-op impl of beehives, for use with instant nectars.
public class DummyHive implements Beehive {
	public static final DummyHive INSTANCE = new DummyHive();
	private DummyHive() {}

	@Override
	public int getHoneyLevel(World world, BlockPos pos, BlockState state) {
		return 0;
	}

	@Override
	public void setHoneyLevel(World world, BlockPos pos, BlockState state, int level) {

	}

	@Override
	public void addHoneyFlavor(World world, BlockPos pos, BlockState state, HoneyFlavor flavor) {

	}

	@Override
	public Object2IntMap<HoneyFlavor> getFlavors(World world, BlockPos pos, BlockState state) {
		return new Object2IntOpenHashMap<>();
	}

	@Override
	public void harvestHoney(World world, BlockPos pos, BlockState state, HoneyFlavor harvested) {

	}
}
