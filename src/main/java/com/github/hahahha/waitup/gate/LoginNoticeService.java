package com.github.hahahha.waitup.gate;

import com.github.hahahha.waitup.compat.ITFRBCompat;
import com.github.hahahha.waitup.config.WaitUpConfig;
import com.github.hahahha.waitup.localization.WaitUpServerText;
import net.minecraft.ChatMessageComponent;
import net.minecraft.EnumChatFormatting;
import net.minecraft.ServerPlayer;

/** Sends a one-time summary of the current Wait Up settings when a player logs in. */
public final class LoginNoticeService {
    private LoginNoticeService() {
    }

    public static void sendConfigSummary(ServerPlayer player) {
        sendMessage(player, WaitUpServerText.loginHeader());
        sendMessage(player, WaitUpServerText.loginDimensionDays(
                WaitUpConfig.getUnderworldMinDay(),
                WaitUpConfig.getNetherMinDay(),
                WaitUpConfig.getEndMinDay()
        ));
        sendMessage(player, WaitUpServerText.loginQuartzDay(
                WaitUpConfig.getNetherQuartzSacrificeMinDay()
        ));
        sendMessage(player, WaitUpServerText.loginGemXp(
                WaitUpConfig.getNetherQuartzSacrificeXp(),
                WaitUpConfig.getEmeraldSacrificeXp(),
                WaitUpConfig.getDiamondSacrificeXp(),
                WaitUpConfig.getLapisSacrificeXp()
        ));

        sendOreSummary(player);
        sendItfrbSummary(player);
    }

    private static void sendOreSummary(ServerPlayer player) {
        int oreRadius = WaitUpConfig.getUnderworldHighOreMaxAbsXZ();
        if (oreRadius <= 0) {
            sendMessage(player, WaitUpServerText.loginOreDisabled());
            return;
        }

        sendMessage(player, WaitUpServerText.loginOreConfig(
                oreRadius,
                WaitUpConfig.getUnderworldHighMithrilMultiplier(),
                WaitUpConfig.getUnderworldHighDiamondMultiplier()
        ));
    }

    private static void sendItfrbSummary(ServerPlayer player) {
        if (!ITFRBCompat.isLoaded()) {
            return;
        }

        if (!WaitUpConfig.isItfrbCompatEnabled()) {
            sendMessage(player, WaitUpServerText.loginItfrbCompatDisabled());
            return;
        }

        int protectedAbsXZ = WaitUpConfig.getItfrbLichCastleProtectedAbsXZ();
        if (protectedAbsXZ <= 0) {
            sendMessage(player, WaitUpServerText.loginItfrbDistanceDisabled());
            return;
        }

        sendMessage(player, WaitUpServerText.loginItfrbDistanceConfig(protectedAbsXZ));
    }

    private static void sendMessage(ServerPlayer player, String text) {
        player.sendChatToPlayer(ChatMessageComponent.createFromText(text).setColor(EnumChatFormatting.YELLOW));
    }
}
