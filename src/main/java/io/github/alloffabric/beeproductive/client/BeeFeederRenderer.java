package io.github.alloffabric.beeproductive.client;

import io.github.alloffabric.beeproductive.block.entity.BeeFeederBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;

public class BeeFeederRenderer extends BlockEntityRenderer<BeeFeederBlockEntity> {
	public BeeFeederRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(BeeFeederBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		ItemRenderer renderer = MinecraftClient.getInstance().getItemRenderer();
		ItemStack stack = blockEntity.getStack(0);
		if (!stack.isEmpty()) {
			matrices.push();
			matrices.translate(.5, 5/16f, .5);
			if (renderer.getHeldItemModel(stack, blockEntity.getWorld(), null).hasDepth()) {
				matrices.translate(0.0, -0.03, 0.0);
				matrices.multiply(Vector3f.NEGATIVE_X.getDegreesQuaternion(-90.0f));
				matrices.scale(.2f, .2f, .2f);
			} else {
				matrices.translate(0.0, -0.064, 0.0);
//				matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0f));
				matrices.multiply(Vector3f.NEGATIVE_X.getDegreesQuaternion(90.0f));
				matrices.scale(.14f, .14f, .14f);
			}
			renderer.renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);
			matrices.pop();
		}
	}
}
