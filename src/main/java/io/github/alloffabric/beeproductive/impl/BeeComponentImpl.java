package io.github.alloffabric.beeproductive.impl;

import io.github.alloffabric.beeproductive.BeeProductive;
import io.github.alloffabric.beeproductive.api.BeeComponent;
import io.github.alloffabric.beeproductive.api.Nectar;
import io.github.alloffabric.beeproductive.api.trait.BeeTrait;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class BeeComponentImpl implements BeeComponent {
	private Map<BeeTrait<?>, TraitValue<?>> traits;
	private Nectar nectar;

	public BeeComponentImpl() {
		this.traits = new HashMap<>();
		this.nectar = Nectar.NONE;
		for (Identifier id : BeeProductive.BEE_TRAITS.getIds()) {
			BeeTrait<?> trait = BeeProductive.BEE_TRAITS.get(id);
			traits.put(trait, trait::getDefaultValue);
		}
	}

	@Override
	public void fromTag(CompoundTag tag) {
		traits.clear();
		CompoundTag traitsTag = tag.getCompound("Traits");
		for(String key : traitsTag.getKeys()) {
			BeeTrait trait = BeeProductive.BEE_TRAITS.get(new Identifier(key));
			traits.put(trait, () -> trait.fromTag(traitsTag.get(key)));
		}
		nectar = BeeProductive.NECTARS.get(new Identifier(tag.getString("nectar")));
	}

	@Override
	@SuppressWarnings("unchecked")
	public CompoundTag toTag(CompoundTag tag) {
		CompoundTag traitsTag = new CompoundTag();
		for (BeeTrait trait : traits.keySet()) {
			Identifier id = BeeProductive.BEE_TRAITS.getId(trait);
			traitsTag.put(id.toString(), trait.toTag(traits.get(trait).get()));
		}
		tag.put("nectar", StringTag.of(BeeProductive.NECTARS.getId(nectar).toString()));
		return tag;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getTraitValue(BeeTrait<T> trait) {
		if (!traits.containsKey(trait)) {
			traits.put(trait, trait::getDefaultValue);
		}
		return (T) traits.get(trait).get();
	}

	@Override
	public <T> void setTraitValue(BeeTrait<T> trait, T value) {
		traits.put(trait, () -> value);
	}

	@Override
	public Nectar getNectar() {
		return nectar;
	}

	@Override
	public void setNectar(Nectar nectar) {
		this.nectar = nectar;
	}

	private interface TraitValue<T> {
		T get();
	}
}
