package io.github.alloffabric.beeproductive;

import io.github.alloffabric.beeproductive.api.BeeComponent;
import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import io.github.alloffabric.beeproductive.api.Nectar;
import io.github.alloffabric.beeproductive.api.trait.BeeTrait;
import io.github.alloffabric.beeproductive.impl.BeeComponentImpl;
import io.github.alloffabric.beeproductive.init.*;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import nerdhub.cardinal.components.api.util.EntityComponents;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.passive.BeeEntity;
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

	@Override
	public void onInitialize() {
		EntityComponentCallback.event(BeeEntity.class).register((entity, container) -> container.put(BEE_COMPONENT, new BeeComponentImpl(entity)));
		EntityComponents.setRespawnCopyStrategy(BEE_COMPONENT, RespawnCopyStrategy.ALWAYS_COPY);
		BeeProdTraits.init();
		BeeProdHoneys.init();
		BeeProdNectars.init();
		BeeProdBlocks.init();
		BeeProdBlockEntities.init();
		BeeProdItems.init();
		BeeProdTags.init();
		BeeProdEvents.init();
		BeeProdLoot.init();
	}
}
