package io.github.alloffabric.beeproductive.mixin;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.BeeComponent;
import io.github.alloffabric.beeproductive.init.BeeTraits;
import net.minecraft.client.render.entity.BeeEntityRenderer;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeeEntityRenderer.class)
public class MixinBeeEntityRenderer {

	@Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
	private void injectCustomSkins(BeeEntity bee, CallbackInfoReturnable<Identifier> info) {
		BeeComponent comp = BeeProductive.BEE_COMPONENT.get(bee);
		Identifier id = comp.getTraitValue(BeeTraits.SKIN);
		if (id.equals(BeeTraits.SKIN.getDefaultValue())) return;
		if (bee.isAngry()) id = new Identifier(id.getNamespace(), id.getPath() + "_angry");
		if (bee.hasNectar()) id = new Identifier(id.getNamespace(), id.getPath() + "_nectar");
		info.setReturnValue(new Identifier(id.getNamespace(), id.getPath() + ".png"));
	}
}
