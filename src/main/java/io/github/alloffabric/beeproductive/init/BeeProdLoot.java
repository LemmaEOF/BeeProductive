package io.github.alloffabric.beeproductive.init;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;

public class BeeProdLoot {

	public static void init() {
		LootTableLoadingCallback.EVENT.register(((resourceManager, lootManager, id, supplier, setter) -> {
			if (id.getPath().contains("chest")) {
				FabricLootPoolBuilder builder = FabricLootPoolBuilder.builder();
				builder.withRolls(UniformLootTableRange.between(0, 5));
				builder.withCondition(RandomChanceLootCondition.builder(0.5f));
				builder.withEntry(ItemEntry.builder(BeeProdItems.TRANS_INSTANT_NECTAR));
				builder.withEntry(ItemEntry.builder(BeeProdItems.NONBINARY_INSTANT_NECTAR));
				builder.withEntry(ItemEntry.builder(BeeProdItems.GAY_INSTANT_NECTAR));
				builder.withEntry(ItemEntry.builder(BeeProdItems.LESBIAN_INSTANT_NECTAR));
				builder.withEntry(ItemEntry.builder(BeeProdItems.BI_INSTANT_NECTAR));
				builder.withEntry(ItemEntry.builder(BeeProdItems.PAN_INSTANT_NECTAR));
				supplier.withPool(builder);
			}
		}));
	}
}
