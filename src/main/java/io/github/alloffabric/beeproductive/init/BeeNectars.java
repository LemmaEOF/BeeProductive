package io.github.alloffabric.beeproductive.init;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.Nectar;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BeeNectars {
	public static final Nectar EMPTY = register("empty", (bee, hive) -> { });

	//functional trait nectars
	public static final Nectar NOCTURNAL = register("nocturnal", (bee, hive) -> BeeProductive.BEE_COMPONENT.get(bee).setTraitValue(BeeTraits.NOCTURNAL, true));
	public static final Nectar WEATHERPROOF = register("weatherproof", (bee, hive) -> BeeProductive.BEE_COMPONENT.get(bee).setTraitValue(BeeTraits.WEATHERPROOF, true));

	//aesthetic trait nectars
	public static final Nectar TRANS_BEE = register("trans_skin", (bee, hive) -> BeeProductive.BEE_COMPONENT.get(bee).setTraitValue(BeeTraits.SKIN, new Identifier(BeeProductive.MODID, "textures/entity/bee/trans_bee")));

	//honey flavor nectars
	public static final Nectar ENDER = register("ender", (bee, hive) -> hive.addHoneyFlavor(BeeHoneys.ENDER));

	public static void init() { }

	private static Nectar register(String name, Nectar nectar) {
		return Registry.register(BeeProductive.NECTARS, new Identifier(BeeProductive.MODID, name), nectar);
	}
}
