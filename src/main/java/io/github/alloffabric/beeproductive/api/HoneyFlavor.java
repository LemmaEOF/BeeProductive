package io.github.alloffabric.beeproductive.api;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

/**
 * A flavor of honey processed by bees in a beehive.
 * Stacks of more than one item will be split into single-item stacks when dropping
 */
//TODO: loot tables instead?
public class HoneyFlavor {
	public static final HoneyFlavor VANILLA = new HoneyFlavor(new ItemStack(Items.HONEYCOMB, 3), new ItemStack(Items.HONEY_BOTTLE));
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
