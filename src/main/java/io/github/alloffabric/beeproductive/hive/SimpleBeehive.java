package io.github.alloffabric.beeproductive.hive;

import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import io.github.alloffabric.beeproductive.hooks.BeehiveAccessor;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * An implementation of Beehive based on a standard vanilla BeeHiveBlock and BeeHiveBlockEntitiy.
 */
public class SimpleBeehive implements Beehive {
	private World world;
	private BlockPos pos;
	private BlockState state;
	private BeehiveAccessor accessor;

	public SimpleBeehive(World world, BlockPos pos, BlockState state) {
		this.world = world;
		this.pos = pos;
		this.state = state;
		this.accessor = (BeehiveAccessor)world.getBlockEntity(pos);
	}

	@Override
	public int getHoneyLevel() {
		return state.get(Properties.HONEY_LEVEL);
	}

	@Override
	public void setHoneyLevel(int level) {
		world.setBlockState(pos, state.with(Properties.HONEY_LEVEL, level));
		state = world.getBlockState(pos);
	}

	@Override
	public void addHoneyFlavor(HoneyFlavor flavor) {
		accessor.beeproductive$addHoneyFlavor(flavor);
	}

	@Override
	public Object2IntMap<HoneyFlavor> getFlavors() {
		return accessor.beeproductive$getHoneyFlavors();
	}

	@Override
	public void harvestHoney(HoneyFlavor harvested) {
		accessor.beeproductive$clearHoneyFlavors();
	}
}
