package io.github.alloffabric.beeproductive.api.trait;

import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

public class StringBeeTrait implements BeeTrait<String> {
	String defaultValue;

	public StringBeeTrait(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}

	@Override
	public Tag toTag(String value) {
		return StringTag.of(value);
	}

	@Override
	public String fromTag(Tag tag) {
		return tag.asString();
	}
}
