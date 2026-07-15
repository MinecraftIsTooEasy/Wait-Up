package com.github.hahahha.waitup.gate;

import com.github.hahahha.waitup.config.WaitUpConfig;
import com.github.hahahha.waitup.localization.WaitUpServerText;
import net.minecraft.ChatMessageComponent;
import net.minecraft.EnumChatFormatting;
import net.minecraft.EnumSignal;
import net.minecraft.Packet85SimpleSignal;
import net.minecraft.ServerPlayer;
import net.minecraft.World;
import net.minecraft.WorldServer;
import net.minecraft.server.MinecraftServer;

/** Shared gate logic used by portal mixins. */
public final class DimensionGateService {
    private DimensionGateService() {
    }

    public static boolean isDimensionLocked(int dimensionId) {
        int requiredDay = getRequiredDay(dimensionId);
        if (requiredDay <= 0) {
            return false;
        }

        WorldServer overworld = getOverworld();
        return overworld != null && overworld.getDayOfWorld() < requiredDay;
    }

    public static void sendPlayerToOverworldSpawn(ServerPlayer player, int blockedDimensionId) {
        MinecraftServer server = MinecraftServer.getServer();
        WorldServer overworld = getOverworld();
        if (server == null || overworld == null) {
            return;
        }

        int currentDay = overworld.getDayOfWorld();
        int requiredDay = getRequiredDay(blockedDimensionId);
        if (requiredDay > 0) {
            sendLockedMessage(player, blockedDimensionId, requiredDay, currentDay);
            sendReturnedToSpawnMessage(player, blockedDimensionId);
        }

        if (player.dimension == World.DIMENSION_ID_OVERWORLD) {
            startSpawnRunegate(player, overworld);
            return;
        }

        double spawnX = (double) overworld.getSpawnX() + 0.5;
        double spawnY = (double) overworld.getTopSolidOrLiquidBlockMITE(overworld.getSpawnX(), overworld.getSpawnZ(), false) + 1.1;
        double spawnZ = (double) overworld.getSpawnZ() + 0.5;
        server.getConfigurationManager().transferPlayerToDimension(player, World.DIMENSION_ID_OVERWORLD);
        player.travelInsideDimension(spawnX, spawnY, spawnZ);
    }

    public static boolean isOverworldBottomPortalLockedForUnderworld(World world, int destinationDimensionId) {
        return world.isOverworld()
                && destinationDimensionId == World.DIMENSION_ID_UNDERWORLD
                && isDimensionLocked(World.DIMENSION_ID_UNDERWORLD);
    }

    private static void startSpawnRunegate(ServerPlayer player, WorldServer overworld) {
        if (player.is_runegate_teleporting) {
            return;
        }

        player.is_runegate_teleporting = true;
        player.runegate_destination_coords = new int[]{
                overworld.getSpawnX(),
                overworld.getTopSolidOrLiquidBlockMITE(overworld.getSpawnX(), overworld.getSpawnZ(), false) + 1,
                overworld.getSpawnZ()
        };
        player.prevent_runegate_achievement = true;
        player.playerNetServerHandler.sendPacketToPlayer(new Packet85SimpleSignal(EnumSignal.runegate_start));
    }

    private static int getRequiredDay(int dimensionId) {
        return switch (dimensionId) {
            case World.DIMENSION_ID_UNDERWORLD -> WaitUpConfig.getUnderworldMinDay();
            case World.DIMENSION_ID_NETHER -> WaitUpConfig.getNetherMinDay();
            case World.DIMENSION_ID_THE_END -> WaitUpConfig.getEndMinDay();
            default -> 0;
        };
    }

    private static void sendLockedMessage(ServerPlayer player, int blockedDimensionId, int requiredDay, int currentDay) {
        String message = blockedDimensionId == World.DIMENSION_ID_UNDERWORLD
                ? WaitUpServerText.underworldLocked(requiredDay, currentDay)
                : WaitUpServerText.otherDimensionLocked(blockedDimensionId, requiredDay, currentDay);
        player.sendChatToPlayer(ChatMessageComponent.createFromText(message).setColor(EnumChatFormatting.YELLOW));
    }

    private static void sendReturnedToSpawnMessage(ServerPlayer player, int blockedDimensionId) {
        String message = blockedDimensionId == World.DIMENSION_ID_UNDERWORLD
                ? WaitUpServerText.underworldPortalReturnedToSpawn()
                : WaitUpServerText.otherPortalReturnedToSpawn();
        player.sendChatToPlayer(ChatMessageComponent.createFromText(message).setColor(EnumChatFormatting.YELLOW));
    }

    private static WorldServer getOverworld() {
        MinecraftServer server = MinecraftServer.getServer();
        return server == null ? null : server.worldServerForDimension(World.DIMENSION_ID_OVERWORLD);
    }
}
