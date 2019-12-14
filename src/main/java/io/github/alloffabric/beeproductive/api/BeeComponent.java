package io.github.alloffabric.beeproductive.api;

import io.github.alloffabric.beeproductive.api.trait.BeeTrait;
import nerdhub.cardinal.components.api.component.Component;

public interface BeeComponent extends Component {
	<T> T getTraitValue(BeeTrait<T> trait);
	<T> void setTraitValue(BeeTrait<T> trait, T value);
	Nectar getNectar();
	void setNectar(Nectar nectar);
}
