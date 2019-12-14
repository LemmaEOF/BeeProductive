package io.github.alloffabric.beeproductive.api;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

/**
 * A flavor of honey processed by bees in a beehive.
 * Stacks of more than one item will be split into single-item stacks when dropping
 */
//TODO: data-driven instead?
public interface HoneyFlavor {
	HoneyFlavor VANILLA = new HoneyFlavor() {
		@Override
		public ItemStack getSheared() {
			return new ItemStack(Items.HONEYCOMB, 3);
		}

		@Override
		public ItemStack getBottled() {
			return new ItemStack(Items.HONEY_BOTTLE);
		}
	};

	/**
	 * @return The result from using shears on a full hive.
	 */
	ItemStack getSheared();

	/**
	 * @return The result from using an empty bottle on a full hive.
	 */
	ItemStack getBottled();
}
