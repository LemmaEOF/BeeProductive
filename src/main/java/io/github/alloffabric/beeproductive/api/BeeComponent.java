package io.github.alloffabric.beeproductive.api;

import io.github.alloffabric.beeproductive.api.trait.BeeTrait;
import nerdhub.cardinal.components.api.util.sync.EntitySyncedComponent;

public interface BeeComponent extends EntitySyncedComponent {
	<T> T getTraitValue(BeeTrait<T> trait);
	<T> void setTraitValue(BeeTrait<T> trait, T value);
	Nectar getNectar();
	void setNectar(Nectar nectar);
}
