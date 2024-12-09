package net.prouncedev.client;

import com.ibm.icu.text.DecimalFormat;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.prouncedev.BetterCooldowns;
import net.prouncedev.config.BetterCooldownsConfig;

public class CooldownHudOverlay implements HudRenderCallback {
    @Override
    //? if >1.20.1 {
    /*public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
    *///?} else
    public void onHudRender(DrawContext drawContext, float v) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            int entrySpacing = BetterCooldowns.getConfig().entrySpacing; // Spacing between entries
            int textPadding = BetterCooldowns.getConfig().textPadding; // Padding between item and text
            int backgroundColor = BetterCooldowns.getConfig().backgroundColor; // Background color
            int textColor = BetterCooldowns.getConfig().textColor; // Text color
            float scale = BetterCooldowns.getConfig().hudScale; // Scaling factor
            int direction = 1;
            int x = 0;
            int y = 0;

            if (BetterCooldowns.getConfig().position == BetterCooldownsConfig.PositionOptions.BOTTOM) {
                direction = -1;
                y = height - 50; // Start position in the center
            } else if (BetterCooldowns.getConfig().position == BetterCooldownsConfig.PositionOptions.CENTER) {
                y = height / 2; // Start position in the center
            } else if (BetterCooldowns.getConfig().position == BetterCooldownsConfig.PositionOptions.TOP) {
                y = 50; // Start position in the center
            }


            ClientPlayerEntity player = client.player;
            if (player != null) {
                ItemCooldownManager cooldownManager = player.getItemCooldownManager();
                PlayerInventory playerInventory = player.getInventory();

                for (int i = 0; i < playerInventory.size(); i++) {
                    ItemStack stack = playerInventory.getStack(i);
                    if (!stack.isEmpty() && cooldownManager.isCoolingDown(stack.getItem())) {
                        float ticksRemaining = getTicksUntilComplete(player, stack.getItem());
                        String timeRemainingString = getCooldownTimeString(ticksRemaining);

                        // Scale text size and measure text width
                        int textWidth = (int) (client.textRenderer.getWidth(timeRemainingString) * scale);

                        // Calculate background dimensions dynamically
                        int itemSize = (int) (16 * scale); // Scaled item size
                        int backgroundHeight = Math.max((int) (18 * scale), (int) (client.textRenderer.fontHeight * scale + 2 * textPadding));
                        int backgroundWidth = Math.max((textWidth + itemSize + (int) (3 * textPadding)), (int) (BetterCooldowns.getConfig().hudWidth * scale));

                        float ticksSinceStart = getTicksSinceStart(player, stack.getItem());
                        float easeProgress = easeIn((float) -backgroundWidth, (float) 0, ticksSinceStart, BetterCooldowns.getConfig().easingDuration);
                        easeProgress += easeIn((float) -backgroundWidth, (float) 0, ticksRemaining, BetterCooldowns.getConfig().easingDuration);
                        // Draw background rectangle
                        drawContext.fill((int)easeProgress + x, y, (int)easeProgress + x + backgroundWidth, y + backgroundHeight, backgroundColor);

                        float itemX = easeProgress + x + (textPadding * scale);
                        float itemY = y + (backgroundHeight / 2F - itemSize / 2F);

                        // Draw cooldown text (scaled)
                        float textX = itemX + itemSize + (textPadding * scale);
                        float textY = y + (backgroundHeight / 2F - ((client.textRenderer.fontHeight - 3) * scale / 2F));
                        drawContext.getMatrices().push();
                        drawContext.getMatrices().scale(scale, scale, scale); // Apply scaling
                        drawContext.drawItem(stack, (int) (itemX / scale), (int) (itemY / scale));
                        drawContext.drawText(client.textRenderer, Text.of(timeRemainingString), (int) (textX / scale), (int) (textY / scale), textColor, true);
                        drawContext.getMatrices().pop();

                        // Move to next entry position
                        y += (backgroundHeight + entrySpacing) * direction;
                    }
                }
            }
        }
    }


    public float getTicksUntilComplete(PlayerEntity player, Item item) {
        float currentProgress = player.getItemCooldownManager().getCooldownProgress(item, 0);
        float nextProgress = player.getItemCooldownManager().getCooldownProgress(item, 1);

        float progressPerTick = currentProgress - nextProgress;

        if (progressPerTick <= 0) {
            return 0;
        }

        return currentProgress / progressPerTick;
    }

    public float getTicksSinceStart(PlayerEntity player, Item item) {
        float currentProgress = player.getItemCooldownManager().getCooldownProgress(item, 0);
        float nextProgress = player.getItemCooldownManager().getCooldownProgress(item, 1);

        float progressPerTick = currentProgress - nextProgress;

        if (progressPerTick <= 0) {
            return 0;
        }
        float remainingProgress = 1.0F - currentProgress;
        return remainingProgress / progressPerTick;
    }

    public String getCooldownTimeString(float ticksLeft) {
        float secondsLeft = ticksLeft / 20.0f;

        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(secondsLeft) + "s";
    }

    public float easeIn(float currentPos, float targetPos, float elapsedTicks, float totalTicks) {
        float progress = MathHelper.clamp(elapsedTicks / totalTicks, 0.0f, 1.0f);
        // Clamp progress to the range [0, 1]
        progress = MathHelper.clamp(progress, 0.0f, 1.0f);

        // Apply the ease-in function (quadratic ease-in: t^2)
        float easedProgress = progress * progress;

        // Interpolate between currentPos and targetPos using the eased progress
        return currentPos + (targetPos - currentPos) * easedProgress;
    }
}
