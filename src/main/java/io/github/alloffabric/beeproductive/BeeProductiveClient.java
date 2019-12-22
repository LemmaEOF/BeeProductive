package io.github.alloffabric.beeproductive;

import io.github.alloffabric.beeproductive.client.BeeFeederRenderer;
import io.github.alloffabric.beeproductive.init.BeeProdBlockEntities;
import io.github.alloffabric.beeproductive.init.BeeProdBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class BeeProductiveClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(BeeProdBlocks.BEE_FEEDER, RenderLayer.getCutout());
		BlockEntityRendererRegistry.INSTANCE.register(BeeProdBlockEntities.FEEDER_ENTITY, BeeFeederRenderer::new);
	}
}
