package io.github.alloffabric.beeproductive;

import io.github.alloffabric.beeproductive.api.BeeComponent;
import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import io.github.alloffabric.beeproductive.impl.BeeComponentImpl;
import io.github.alloffabric.beeproductive.api.trait.BeeTrait;
import io.github.alloffabric.beeproductive.api.Nectar;
import io.github.alloffabric.beeproductive.api.trait.BooleanBeeTrait;
import io.github.alloffabric.beeproductive.api.trait.IdentifierBeeTrait;
import io.github.alloffabric.beeproductive.item.InstantNectarItem;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import nerdhub.cardinal.components.api.util.EntityComponents;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BeeProductive implements ModInitializer {
	public static final String MODID = "beeproductive";

	public static final Logger logger = LogManager.getLogger(MODID);

	public static final ComponentType<BeeComponent> BEE_COMPONENT = ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(MODID, "bee_component"), BeeComponent.class);

	public static final Registry<BeeTrait<?>> BEE_TRAITS = new SimpleRegistry<>();
	public static final Registry<Nectar> NECTARS = new DefaultedRegistry<>("beeproductive:none");
	public static final Registry<HoneyFlavor> HONEY_FLAVORS = new DefaultedRegistry<>("beeproductive:vanilla");

	public static final BeeTrait<Boolean> NOCTURNAL = register("nocturnal", new BooleanBeeTrait(false));
	public static final BeeTrait<Boolean> WEATHERPROOF = register("weatherproof", new BooleanBeeTrait(false));
	public static final BeeTrait<Identifier> SKIN = register("skin", new IdentifierBeeTrait(new Identifier("textures/entity/bee/bee")));

	public static final Tag<Item> BEE_TEMPTING = TagRegistry.item(new Identifier(MODID, "bee_tempting"));
	public static final Tag<Block> BEE_FEEDING = TagRegistry.block(new Identifier(MODID, "bee_feeding"));

	public static final HoneyFlavor TEST_FLAVOR = register("test", new HoneyFlavor(new ItemStack(Items.SLIME_BALL, 3), new ItemStack(Items.SLIME_BLOCK)));

	public static final Nectar TRANS_BEE = register("trans_bee", (bee, hive) -> BEE_COMPONENT.get(bee).setTraitValue(SKIN, new Identifier(MODID, "textures/entity/bee/trans_bee")));
	public static final Item TRANS_INSTANT_NECTAR = register("trans_bee_nectar", new InstantNectarItem(TRANS_BEE, new Item.Settings().group(ItemGroup.MISC)));

	@Override
	public void onInitialize() {
		Registry.register(NECTARS, new Identifier(MODID, "none"), Nectar.NONE);
		Registry.register(HONEY_FLAVORS, new Identifier(MODID, "vanilla"), HoneyFlavor.VANILLA);
		EntityComponentCallback.event(BeeEntity.class).register((entity, container) -> container.put(BEE_COMPONENT, new BeeComponentImpl(entity)));
		EntityComponents.setRespawnCopyStrategy(BEE_COMPONENT, RespawnCopyStrategy.ALWAYS_COPY);
	}

	private static <T> BeeTrait<T> register(String name, BeeTrait<T> trait) {
		return Registry.register(BEE_TRAITS, new Identifier(MODID, name), trait);
	}

	private static Nectar register(String name, Nectar nectar) {
		return Registry.register(NECTARS, new Identifier(MODID, name), nectar);
	}

	private static HoneyFlavor register(String name, HoneyFlavor flavor) {
		return Registry.register(HONEY_FLAVORS, new Identifier(MODID, name), flavor);
	}

	private static Item register(String name, Item item) {
		return Registry.register(Registry.ITEM, new Identifier(MODID, name), item);
	}
}
