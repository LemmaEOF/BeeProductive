package io.github.alloffabric.beeproductive;

import io.github.alloffabric.beeproductive.api.BeeTrait;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BeeProductive implements ModInitializer {
	public static final String MODID = "beeproductive";

	public static final Logger logger = LogManager.getLogger(MODID);

	public static final Registry<BeeTrait<?>> BEE_TRAITS = new SimpleRegistry<>();

	@Override
	public void onInitialize() {

	}
}
