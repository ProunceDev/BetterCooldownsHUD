package net.prouncedev.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.Screen;
import net.prouncedev.BetterCooldowns;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (Screen parent) -> BetterCooldowns.isClothConfigLoaded() ? AutoConfig.getConfigScreen(BetterCooldownsConfig.class, parent).get() : null;
    }
}