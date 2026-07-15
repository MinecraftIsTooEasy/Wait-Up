package com.github.hahahha.waitup.gate;

import com.github.hahahha.waitup.config.WaitUpConfig;
import com.github.hahahha.waitup.localization.WaitUpServerText;
import net.minecraft.ChatMessageComponent;
import net.minecraft.EnumChatFormatting;
import net.minecraft.EntityPlayer;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.World;
import net.minecraft.WorldServer;
import net.minecraft.server.MinecraftServer;

/** Controls the day lock for sacrificing Nether Quartz into experience. */
public final class QuartzSacrificeService {
    private QuartzSacrificeService() {
    }

    public static boolean shouldBlockNetherQuartzSacrifice(EntityPlayer player) {
        if (!isNetherQuartzSacrificeLocked()) {
            return false;
        }

        if (player.onServer()) {
            sendLockedMessage(player);
        }

        return true;
    }

    public static boolean shouldBlockNetherQuartzSacrifice(ItemStack itemStack) {
        return isNetherQuartz(itemStack) && isNetherQuartzSacrificeLocked();
    }

    public static boolean shouldZeroNetherQuartzSacrificeExperience(ItemStack itemStack) {
        return isNetherQuartz(itemStack) && isNetherQuartzSacrificeLocked();
    }

    public static boolean isNetherQuartz(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() == Item.netherQuartz;
    }

    public static boolean isNetherQuartzSacrificeLocked() {
        int requiredDay = WaitUpConfig.getNetherQuartzSacrificeMinDay();
        if (requiredDay <= 0) {
            return false;
        }

        WorldServer overworld = getOverworld();
        return overworld != null && overworld.getDayOfWorld() < requiredDay;
    }

    public static void sendLockedMessage(EntityPlayer player) {
        sendDayLockedMessage(player, LockedMessageType.NORMAL);
    }

    public static void sendItfrbReserverLockedMessage(EntityPlayer player) {
        sendDayLockedMessage(player, LockedMessageType.ITFRB_RESERVER);
    }

    public static void sendItfrbAbsorbLockedMessage(EntityPlayer player) {
        sendDayLockedMessage(player, LockedMessageType.ITFRB_ABSORB);
    }

    private static void sendDayLockedMessage(EntityPlayer player, LockedMessageType messageType) {
        if (!player.onServer()) {
            return;
        }

        int requiredDay = WaitUpConfig.getNetherQuartzSacrificeMinDay();
        WorldServer overworld = getOverworld();
        if (requiredDay <= 0 || overworld == null) {
            return;
        }

        int currentDay = overworld.getDayOfWorld();
        String message = switch (messageType) {
            case NORMAL -> WaitUpServerText.netherQuartzSacrificeLocked(requiredDay, currentDay);
            case ITFRB_RESERVER -> WaitUpServerText.netherQuartzItfrbReserverLocked(requiredDay, currentDay);
            case ITFRB_ABSORB -> WaitUpServerText.netherQuartzItfrbAbsorbLocked(requiredDay, currentDay);
        };
        player.sendChatToPlayer(ChatMessageComponent.createFromText(message).setColor(EnumChatFormatting.YELLOW));
    }

    private static WorldServer getOverworld() {
        MinecraftServer server = MinecraftServer.getServer();
        return server == null ? null : server.worldServerForDimension(World.DIMENSION_ID_OVERWORLD);
    }

    private enum LockedMessageType {
        NORMAL,
        ITFRB_RESERVER,
        ITFRB_ABSORB
    }
}
