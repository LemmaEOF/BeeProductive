package io.github.alloffabric.beeproductive.api;

import io.github.alloffabric.beeproductive.BeeProductive;
import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class BeeComponent implements Component {
	private Map<BeeTrait<?>, TraitValue<?>> traits = new HashMap<>();

	@Override
	public void fromTag(CompoundTag tag) {
		traits.clear();
		CompoundTag traitsTag = tag.getCompound("Traits");
		for(String key : traitsTag.getKeys()) {
			BeeTrait trait = BeeProductive.BEE_TRAITS.get(new Identifier(key));
			traits.put(trait, () -> trait.fromTag(traitsTag.get(key)));
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public CompoundTag toTag(CompoundTag tag) {
		CompoundTag traitsTag = new CompoundTag();
		for (BeeTrait trait : traits.keySet()) {
			Identifier id = BeeProductive.BEE_TRAITS.getId(trait);
			traitsTag.put(id.toString(), trait.toTag(traits.get(trait).get()));
		}
		return tag;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getTraitValue(BeeTrait<T> trait) {
		return (T) traits.get(trait).get();
	}

	public <T> void setTraitValue(BeeTrait<T> trait, T value) {
		traits.put(trait, () -> value);
	}

	private interface TraitValue<T> {
		T get();
	}
}
