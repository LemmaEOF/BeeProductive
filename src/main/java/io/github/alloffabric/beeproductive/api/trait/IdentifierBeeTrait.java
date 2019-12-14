package io.github.alloffabric.beeproductive.api.trait;

import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Identifier;

public class IdentifierBeeTrait implements BeeTrait<Identifier> {
	private Identifier defaultValue;

	public IdentifierBeeTrait(Identifier defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public Identifier getDefaultValue() {
		return defaultValue;
	}

	@Override
	public Tag toTag(Identifier value) {
		return StringTag.of(value.toString());
	}

	@Override
	public Identifier fromTag(Tag tag) {
		return new Identifier(tag.asString());
	}
}
