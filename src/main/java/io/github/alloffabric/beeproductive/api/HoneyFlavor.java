package io.github.alloffabric.beeproductive.api;

import net.minecraft.item.ItemStack;

/**
 * A flavor of honey processed by bees in a beehive.
 * Stacks of more than one item will be split into single-item stacks when dropping
 */
//TODO: loot tables instead?
public class HoneyFlavor {
	private ItemStack sheared;
	private ItemStack bottled;

	public HoneyFlavor(ItemStack sheared, ItemStack bottled) {
		this.sheared = sheared;
		this.bottled = bottled;
	}

	/**
	 * @return The result from using shears on a full hive.
	 */
	public ItemStack getSheared() {
		return sheared.copy();
	}

	/**
	 * @return The result from using an empty bottle on a full hive.
	 */
	public ItemStack getBottled() {
		return bottled.copy();
	}
}
