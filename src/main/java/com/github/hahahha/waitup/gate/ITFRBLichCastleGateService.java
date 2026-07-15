package com.github.hahahha.waitup.gate;

import com.github.hahahha.waitup.compat.ITFRBCompat;
import com.github.hahahha.waitup.config.WaitUpConfig;
import net.minecraft.World;

/** Restricts natural ITF Reborn Lich Castle generation in the protected central |x|/|z| region. */
public final class ITFRBLichCastleGateService {
    private ITFRBLichCastleGateService() {
    }

    public static boolean shouldCancelNaturalGeneration(World world, int x, int z) {
        if (!world.isUnderworld() || !ITFRBCompat.isLichCastleDistanceGateEnabled()) {
            return false;
        }

        int protectedAbsXZ = WaitUpConfig.getItfrbLichCastleProtectedAbsXZ();
        return Math.abs(x) <= protectedAbsXZ && Math.abs(z) <= protectedAbsXZ;
    }
}
