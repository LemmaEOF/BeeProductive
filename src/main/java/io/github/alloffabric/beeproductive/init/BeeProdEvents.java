package io.github.alloffabric.beeproductive.init;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.BeeComponent;
import io.github.cottonmc.beehooks.api.BeeTimeCheckCallback;
import io.github.cottonmc.beehooks.api.BeeWeatherCheckCallback;

public class BeeProdEvents {

	public static void init() {
		BeeTimeCheckCallback.EVENT.register((world, bee) -> {
			BeeComponent comp = BeeProductive.BEE_COMPONENT.get(bee);
			return comp.getTraitValue(BeeProdTraits.NOCTURNAL);
		});

		BeeWeatherCheckCallback.EVENT.register((world, bee) -> {
			BeeComponent comp = BeeProductive.BEE_COMPONENT.get(bee);
			return comp.getTraitValue(BeeProdTraits.WEATHERPROOF);
		});
	}

}
