package net.lostluma.moss_carpet;

import carpet.api.settings.Rule;
import carpet.api.settings.RuleCategory;

public class MossCarpetSettings {
	public static final String MOSS = "moss";

	@Rule(
		strict = false,
		options = {"0", "10", "16"},
		categories = {MOSS, RuleCategory.SURVIVAL}
	)
	public static int insomniaDisabledDistance = 0;
}
