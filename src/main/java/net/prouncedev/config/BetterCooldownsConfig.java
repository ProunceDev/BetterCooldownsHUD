package net.prouncedev.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.prouncedev.BetterCooldowns;

import static me.shedaniel.autoconfig.annotation.Config.Gui.Background.TRANSPARENT;

@Config(name = BetterCooldowns.MOD_ID)
@Config.Gui.Background(TRANSPARENT)
public class BetterCooldownsConfig implements ConfigData {
    public int entrySpacing = 5;
    public int hudWidth = 50;
    public int textPadding = 2;
    public float hudScale = 1.5F;
    public float easingDuration = 5;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int backgroundColor = 0x77111111;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int textColor = 0xFFFFFFFF;
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public PositionOptions position = PositionOptions.BOTTOM;

    public enum PositionOptions { BOTTOM, CENTER, TOP }
}