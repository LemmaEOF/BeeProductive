package io.github.alloffabric.beeproductive.hooks;

import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public interface BeehiveAccessor {
	Object2IntMap<HoneyFlavor> beeproductive$getHoneyFlavors();

	void beeproductive$addHoneyFlavor(HoneyFlavor flavor);

	void beeproductive$harvestHoneyFlavor(HoneyFlavor flavor);
}
