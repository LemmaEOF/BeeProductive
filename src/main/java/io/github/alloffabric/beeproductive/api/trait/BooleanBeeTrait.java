package io.github.alloffabric.beeproductive.api.trait;

import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.Tag;

public class BooleanBeeTrait implements BeeTrait<Boolean> {
	private boolean defaultValue;

	public BooleanBeeTrait(Boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public Boolean getDefaultValue() {
		return defaultValue;
	}

	@Override
	public Tag toTag(Boolean value) {
		return ByteTag.of(value);
	}

	@Override
	public Boolean fromTag(Tag tag) {
		return ((ByteTag)tag).getByte() != 0;
	}
}
