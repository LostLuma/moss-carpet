package net.lostluma.moss_carpet;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import carpet.CarpetExtension;
import carpet.CarpetServer;

public class MossCarpet implements CarpetExtension {
	public static final Gson GSON = new Gson();
	public static final String MOD_ID = "moss_carpet";

	public static void init() {
		CarpetServer.manageExtension(new MossCarpet());
	}

	@Override
	public void onGameStarted() {
		CarpetServer.settingsManager.parseSettingsClass(MossCarpetSettings.class);
	}

	private String languageFile(String language) {
		return String.join("/", "assets", MOD_ID, "lang", language + ".json");
	}

	@Override
	public Map<String, String> canHasTranslations(String language) {
		String data;
		Optional<ModContainer> optional = QuiltLoader.getModContainer(MOD_ID);

		if (optional.isEmpty()) {
			throw new RuntimeException("Failed to find own mod container!");
		}

		try {
			data = Files.readString(optional.get().getPath(languageFile(language)));
		} catch (IOException e) {
			return Collections.emptyMap();
		}

		return GSON.fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
	}
}
