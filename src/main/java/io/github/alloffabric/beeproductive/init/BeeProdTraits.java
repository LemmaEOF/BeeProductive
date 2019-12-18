package io.github.alloffabric.beeproductive.init;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.trait.BeeTrait;
import io.github.alloffabric.beeproductive.api.trait.BooleanBeeTrait;
import io.github.alloffabric.beeproductive.api.trait.IdentifierBeeTrait;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BeeProdTraits {
	//functional traits
	public static final BeeTrait<Boolean> NOCTURNAL = register("nocturnal", new BooleanBeeTrait(false));
	public static final BeeTrait<Boolean> WEATHERPROOF = register("weatherproof", new BooleanBeeTrait(false));
	public static final BeeTrait<Boolean> PACIFIST = register("pacifist", new BooleanBeeTrait(false));

	//aesthetic traits
	//TODO: register skins / provide more info about them somehow?
	public static final BeeTrait<Identifier> SKIN = register("skin", new IdentifierBeeTrait(new Identifier("textures/entity/bee/bee")));

	public static void init() { }

	private static <T> BeeTrait<T> register(String name, BeeTrait<T> trait) {
		return Registry.register(BeeProductive.BEE_TRAITS, new Identifier(BeeProductive.MODID, name), trait);
	}
}
