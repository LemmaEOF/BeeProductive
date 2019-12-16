package io.github.alloffabric.beeproductive.api;

import io.github.alloffabric.beeproductive.api.hive.Beehive;
import net.minecraft.entity.passive.BeeEntity;

/**
 * Types of nectar which can be applied to bees to modify their traits or change properties of their hive.
 */
public interface Nectar {

	/**
	 * Effects applied after a bee has collected this nectar, returned to the hive, and processed this nectar.
	 * @param bee The bee that processed this nectar.
	 * @param hive The hive this nectar was processed in.
	 */
	void onApply(BeeEntity bee, Beehive hive);
}
