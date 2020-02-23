package io.github.alloffabric.beeproductive.init;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.Nectar;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BeeProdNectars {
	public static final Nectar EMPTY = register("empty", (bee, hive) -> { });

	//functional trait nectars
	public static final Nectar NOCTURNAL = register("nocturnal", (bee, hive) -> BeeProductive.BEE_COMPONENT.get(bee).setTraitValue(BeeProdTraits.NOCTURNAL, true));
	public static final Nectar WEATHERPROOF = register("weatherproof", (bee, hive) -> BeeProductive.BEE_COMPONENT.get(bee).setTraitValue(BeeProdTraits.WEATHERPROOF, true));
	public static final Nectar PACIFIST = register("pacifist", (bee, hive) -> BeeProductive.BEE_COMPONENT.get(bee).setTraitValue(BeeProdTraits.PACIFIST, true));

	//aesthetic trait nectars
	public static final Nectar TRANS_SKIN = register("trans_skin", (bee, hive) -> BeeProductive.BEE_COMPONENT.get(bee).setTraitValue(BeeProdTraits.SKIN, new Identifier(BeeProductive.MODID, "textures/entity/bee/trans_bee")));
	public static final Nectar NONBINARY_SKIN = register("nonbinary_skin", (bee, hive) -> BeeProductive.BEE_COMPONENT.get(bee).setTraitValue(BeeProdTraits.SKIN, new Identifier(BeeProductive.MODID, "textures/entity/bee/nonbinary_bee")));
	public static final Nectar GAY_SKIN = register("gay_skin", (bee, hive) -> BeeProductive.BEE_COMPONENT.get(bee).setTraitValue(BeeProdTraits.SKIN, new Identifier(BeeProductive.MODID, "textures/entity/bee/gay_bee")));
	public static final Nectar LESBIAN_SKIN = register("lesbian_skin", (bee, hive) -> BeeProductive.BEE_COMPONENT.get(bee).setTraitValue(BeeProdTraits.SKIN, new Identifier(BeeProductive.MODID, "textures/entity/bee/lesbian_bee")));
	public static final Nectar BI_SKIN = register("bi_skin", (bee, hive) -> BeeProductive.BEE_COMPONENT.get(bee).setTraitValue(BeeProdTraits.SKIN, new Identifier(BeeProductive.MODID, "textures/entity/bee/bi_bee")));
	public static final Nectar PAN_SKIN = register("pan_skin", (bee, hive) -> BeeProductive.BEE_COMPONENT.get(bee).setTraitValue(BeeProdTraits.SKIN, new Identifier(BeeProductive.MODID, "textures/entity/bee/pan_bee")));
	public static final Nectar ACE_SKIN = register("ace_skin", (bee, hive) -> BeeProductive.BEE_COMPONENT.get(bee).setTraitValue(BeeProdTraits.SKIN, new Identifier(BeeProductive.MODID, "textures/entity/bee/ace_bee")));

	//honey flavor nectars
	public static final Nectar ENDER = register("ender", (bee, hive) -> hive.addHoneyFlavor(BeeProdHoneys.ENDER));

	public static void init() { }

	private static Nectar register(String name, Nectar nectar) {
		return Registry.register(BeeProductive.NECTARS, new Identifier(BeeProductive.MODID, name), nectar);
	}
}
