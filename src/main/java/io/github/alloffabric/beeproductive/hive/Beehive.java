package io.github.alloffabric.beeproductive.hive;

import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import io.github.alloffabric.beeproductive.init.BeeHoneys;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

/**
 * An interface so that beehives from other mods can interact with honey and nectar compliantly.
 */
public interface Beehive {
	/**
	 * Get the amount of honey in a hive. A level of 5 means one harvest is ready.
	 * @return The current level of honey in the hive.
	 */
	int getHoneyLevel();

	/**
	 * Set the amount of honey in a hive. A level of 5 means one harvest is ready.
	 * @param level The level of honey to set.
	 */
	void setHoneyLevel(int level);

	/**
	 * Add one point to a given honey flavor. The flavor with a majority of points in a hive will be harvested.
	 * @param flavor The flavor to add a point for.
	 */
	void addHoneyFlavor(HoneyFlavor flavor);

	/**
	 * Get the current point spread for flavors in the hive.
	 * @return A map of flavors to their point totals.
	 */
	Object2IntMap<HoneyFlavor> getFlavors();

	/**
	 * Decreate a honey flavor's points count after that flavor has been harvested.
	 * @param harvested The flavor that has been harvested.
	 */
	void harvestHoney(HoneyFlavor harvested);

	/**
	 * Calculate the honey flavor to drop.
	 * @return The flavor with the greatest amount of points over 3. If there are no honeys with a majority share, return vanilla honey.
	 */
	default HoneyFlavor getFlavorToHarvest() {
		Object2IntMap<HoneyFlavor> flavors = getFlavors();
		HoneyFlavor toDrop = BeeHoneys.VANILLA;
		int most = 0;
		for (HoneyFlavor flavor : flavors.keySet()) {
			int amount = flavors.getInt(flavor);
			//check most *and* if it's a majority of one harvest, in case of hives which store more than 5 units
			if (amount > 3 && amount > most) {
				toDrop = flavor;
				most = amount;
			}
		}
		harvestHoney(toDrop);
		return toDrop;
	}

	//TODO: what else should beehives provide?
}
