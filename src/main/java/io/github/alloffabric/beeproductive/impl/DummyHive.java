package io.github.alloffabric.beeproductive.impl;

import io.github.alloffabric.beeproductive.api.hive.Beehive;
import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

//no-op impl of beehives, for use with instant nectars.
public class DummyHive implements Beehive {
	public static final DummyHive INSTANCE = new DummyHive();
	private DummyHive() {}


	@Override
	public int getHoneyLevel() {
		return 0;
	}

	@Override
	public void setHoneyLevel(int level) {

	}

	@Override
	public void addHoneyFlavor(HoneyFlavor flavor) {

	}

	@Override
	public Object2IntMap<HoneyFlavor> getFlavors() {
		return new Object2IntOpenHashMap<>();
	}

	@Override
	public void harvestHoney(HoneyFlavor harvested) {

	}
}
