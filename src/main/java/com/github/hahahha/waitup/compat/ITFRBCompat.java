package com.github.hahahha.waitup.compat;

import com.github.hahahha.waitup.config.WaitUpConfig;
import moddedmite.rustedironcore.api.util.FabricUtil;

/** Helpers for optional ITF Reborn compatibility. */
public final class ITFRBCompat {
    public static final String MOD_ID = "mite-itf-reborn";
    public static final String LICH_CASTLE_CLASS = "net.oilcake.mitelros.world.WorldGenUnderworldCastle";
    public static final String ENCHANT_RESERVER_TILE_CLASS = "net.oilcake.mitelros.block.entity.TileEntityEnchantReserver";
    public static final String ENCHANT_RESERVER_INPUT_SLOT_CLASS = "net.oilcake.mitelros.inventory.EnchantReserverInventory$1";
    public static final String ENCHANT_RESERVER_CONTAINER_CLASS = "net.oilcake.mitelros.container.ContainerEnchantReserver";

    private ITFRBCompat() {
    }

    public static boolean isLoaded() {
        return FabricUtil.isModLoaded(MOD_ID);
    }

    public static boolean isCompatEnabled() {
        return isLoaded() && WaitUpConfig.isItfrbCompatEnabled();
    }

    public static boolean isLichCastleDistanceGateEnabled() {
        return isLoaded()
                && WaitUpConfig.isItfrbCompatEnabled()
                && WaitUpConfig.getItfrbLichCastleProtectedAbsXZ() > 0;
    }
}
