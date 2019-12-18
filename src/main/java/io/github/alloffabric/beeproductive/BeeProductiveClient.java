package io.github.alloffabric.beeproductive;

import io.github.alloffabric.beeproductive.init.BeeProdBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class BeeProductiveClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(BeeProdBlocks.BEE_FEEDER, RenderLayer.getCutout());
	}
}
