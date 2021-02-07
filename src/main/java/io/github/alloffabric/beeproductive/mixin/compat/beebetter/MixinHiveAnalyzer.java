package io.github.alloffabric.beeproductive.mixin.compat.beebetter;

import com.github.draylar.beebetter.item.HiveAnalyzerItem;
import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import io.github.alloffabric.beeproductive.api.hive.Beehive;
import io.github.alloffabric.beeproductive.api.hive.BeehiveProvider;
import io.github.alloffabric.beeproductive.init.BeeProdHoneys;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HiveAnalyzerItem.class)
public class MixinHiveAnalyzer {

	@Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getItemCooldownManager()Lnet/minecraft/entity/player/ItemCooldownManager;"))
	public void injectFlavorMessage(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() instanceof BeehiveProvider) {
			Beehive hive = ((BeehiveProvider) state.getBlock()).getBeehive(world, pos, state);
			if (hive.getHoneyLevel() >= 5) {
				HoneyFlavor flavor = hive.getFlavorToHarvest();
				if (flavor != BeeProdHoneys.VANILLA) {
					Identifier flavorId = BeeProductive.HONEY_FLAVORS.getId(flavor);
					context.getPlayer().sendMessage(new TranslatableText("msg.beeproductive.honey_flavor", new TranslatableText("honey." + flavorId.getNamespace() + "." + flavorId.getPath()).asString()), false);
				} else {
					context.getPlayer().sendMessage(new TranslatableText("msg.beeproductive.honey_vanilla"), false);
				}
			} else {
				context.getPlayer().sendMessage(new TranslatableText("msg.beeproductive.no_honey"), false);
			}
		}
	}
}
