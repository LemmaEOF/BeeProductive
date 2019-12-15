package io.github.alloffabric.beeproductive.item;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.BeeComponent;
import io.github.alloffabric.beeproductive.api.HoneyFlavor;
import io.github.alloffabric.beeproductive.api.trait.BeeTrait;
import io.github.alloffabric.beeproductive.hive.Beehive;
import io.github.alloffabric.beeproductive.hive.BeehiveProvider;
import io.github.alloffabric.beeproductive.init.BeeHoneys;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BeeSwabItem extends Item {
	public BeeSwabItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		if (world.isClient) return ActionResult.PASS;
		if (state.getBlock() instanceof BeehiveProvider) {
			Beehive hive = ((BeehiveProvider)state.getBlock()).getBeehive(world, pos, state);
			if (hive.getHoneyLevel() >= 5) {
				HoneyFlavor flavor = hive.getFlavorToHarvest();
				if (flavor != BeeHoneys.VANILLA) {
					Identifier flavorId = BeeProductive.HONEY_FLAVORS.getId(flavor);
					context.getPlayer().addChatMessage(new TranslatableText("msg.beeproductive.honey_flavor", new TranslatableText("honey." + flavorId.getNamespace() + "." + flavorId.getPath()).asString()), true);
				} else {
					context.getPlayer().addChatMessage(new TranslatableText("msg.beeproductive.honey_vanilla"), true);
				}
			} else {
				context.getPlayer().addChatMessage(new TranslatableText("msg.beeproductive.no_honey"), true);
			}
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	@Override
	public boolean useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (!user.world.isClient && entity instanceof BeeEntity) {
			BeeComponent component = BeeProductive.BEE_COMPONENT.get(entity);
			user.addChatMessage(new TranslatableText("msg.beeproductive.traits"), false);
			for (Identifier id : BeeProductive.BEE_TRAITS.getIds()) {
				BeeTrait<?> trait = BeeProductive.BEE_TRAITS.get(id);
				String value = component.getTraitValue(trait).toString();
				user.addChatMessage(new TranslatableText("msg.beeproductive.trait.display", new TranslatableText("trait." + id.getNamespace() + "." + id.getPath()).asString(), value).formatted(Formatting.GREEN), false);
			}
		}
		return false;
	}
}
