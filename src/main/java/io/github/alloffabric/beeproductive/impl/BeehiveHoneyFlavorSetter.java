package io.github.alloffabric.beeproductive.impl;

import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public interface BeehiveHoneyFlavorSetter {
	Object2IntMap<HoneyFlavor> beeproductive$getHoneyFlavors();

	void beeproductive$addHoneyFlavor(HoneyFlavor flavor);

	void beeproductive$clearHoneyFlavors();
}
