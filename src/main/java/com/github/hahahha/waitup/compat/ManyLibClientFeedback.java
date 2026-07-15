package com.github.hahahha.waitup.compat;

import com.github.hahahha.waitup.localization.WaitUpLocalization;
import net.minecraft.ChatMessageComponent;
import net.minecraft.EnumChatFormatting;
import net.minecraft.Minecraft;

/** Client-only ManyLib feedback kept separate so the dedicated server never touches Minecraft client classes. */
final class ManyLibClientFeedback {
    private ManyLibClientFeedback() {
    }

    static void warnIfRemoteServerConfigWillNotChange() {
        Minecraft minecraft = Minecraft.theMinecraft;
        if (minecraft == null || minecraft.theWorld == null || minecraft.isIntegratedServerRunning()) {
            return;
        }

        if (minecraft.ingameGUI != null) {
            minecraft.ingameGUI.getChatGUI().printChatMessage(
                    ChatMessageComponent.createFromTranslationKey(
                            WaitUpLocalization.MANYLIB_LOCAL_ONLY.getKey()
                    ).setColor(EnumChatFormatting.YELLOW).toStringWithFormatting(true)
            );
        }
    }

    static void warnIfSaveFailed() {
        Minecraft minecraft = Minecraft.theMinecraft;
        if (minecraft == null || minecraft.ingameGUI == null) {
            return;
        }

        minecraft.ingameGUI.getChatGUI().printChatMessage(
                ChatMessageComponent.createFromText(
                        "Wait Up: failed to save the local config file. Check the game log for details."
                ).setColor(EnumChatFormatting.RED).toStringWithFormatting(true)
        );
    }
}
