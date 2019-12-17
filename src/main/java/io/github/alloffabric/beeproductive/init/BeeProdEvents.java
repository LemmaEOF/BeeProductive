package io.github.alloffabric.beeproductive.init;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.BeeComponent;
import io.github.cottonmc.beecompatible.api.BeeTimeCheckCallback;
import io.github.cottonmc.beecompatible.api.BeeWeatherCheckCallback;
import net.fabricmc.fabric.api.util.TriState;

public class BeeProdEvents {

	public static void init() {
		BeeTimeCheckCallback.EVENT.register((world, bee) -> {
			BeeComponent comp = BeeProductive.BEE_COMPONENT.get(bee);
			if (comp.getTraitValue(BeeProdTraits.NOCTURNAL)) return TriState.TRUE;
			return TriState.DEFAULT;
		});

		BeeWeatherCheckCallback.EVENT.register((world, bee) -> {
			BeeComponent comp = BeeProductive.BEE_COMPONENT.get(bee);
			if (comp.getTraitValue(BeeProdTraits.WEATHERPROOF)) return TriState.TRUE;
			return TriState.DEFAULT;
		});
	}

}
