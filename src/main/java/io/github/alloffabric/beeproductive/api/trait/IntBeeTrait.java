package io.github.alloffabric.beeproductive.api.trait;

import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;

public class IntBeeTrait implements BeeTrait<Integer> {
	private int defaultValue;

	public IntBeeTrait(int defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public Integer getDefaultValue() {
		return defaultValue;
	}

	@Override
	public Tag toTag(Integer value) {
		return IntTag.of(value);
	}

	@Override
	public Integer fromTag(Tag tag) {
		return ((IntTag)tag).getInt();
	}
}
