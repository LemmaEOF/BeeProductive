package io.github.alloffabric.beeproductive.api;

import net.minecraft.nbt.Tag;

public interface BeeTrait<T> {
	T getDefaultValue();

	Tag toTag(T value);

	T fromTag(Tag tag);
}
