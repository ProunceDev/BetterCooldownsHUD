package net.prouncedev;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.prouncedev.client.CooldownHudOverlay;
import net.prouncedev.config.BetterCooldownsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterCooldowns implements ModInitializer {
	public static final String MOD_ID = "bettercooldowns";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static BetterCooldownsConfig config;
	@Override
	public void onInitialize() {

		LOGGER.info("BetterCooldowns Loaded");

		if (isClothConfigLoaded()) {
			ConfigHolder<BetterCooldownsConfig> configHolder = AutoConfig.register(BetterCooldownsConfig.class, GsonConfigSerializer::new);
			BetterCooldowns.config = configHolder.getConfig();
		}

		HudRenderCallback.EVENT.register(new CooldownHudOverlay());
	}

	public static BetterCooldownsConfig getConfig() {
		return BetterCooldowns.config;
	}

	public static boolean isClothConfigLoaded() {
		return FabricLoader.getInstance().isModLoaded("cloth-config2");
	}
}