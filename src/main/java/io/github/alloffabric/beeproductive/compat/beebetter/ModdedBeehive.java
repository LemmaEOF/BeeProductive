package io.github.alloffabric.beeproductive.compat.beebetter;

import com.github.draylar.beebetter.block.ModdedBeehiveBlock;
import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import io.github.alloffabric.beeproductive.api.hive.Beehive;
import io.github.alloffabric.beeproductive.hooks.BeehiveAccessor;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModdedBeehive implements Beehive {
	private World world;
	private BlockPos pos;
	private BlockState state;
	private ModdedBeehiveBlock block;
	private BeehiveAccessor accessor;

	public ModdedBeehive(World world, BlockPos pos, BlockState state) {
		this.world = world;
		this.pos = pos;
		this.state = state;
		this.block = (ModdedBeehiveBlock)state.getBlock();
		this.accessor = (BeehiveAccessor)world.getBlockEntity(pos);
	}

	@Override
	public int getHoneyLevel() {
		return block.getHoneyLevel(state);
	}

	@Override
	public void setHoneyLevel(int level) {
		block.setHoneyLevel(world, state, pos, level);
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
		accessor.beeproductive$harvestHoneyFlavor(harvested);
	}
}
