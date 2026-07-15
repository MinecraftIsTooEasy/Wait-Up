package com.github.hahahha.waitup.gate;

import com.github.hahahha.waitup.config.WaitUpConfig;
import net.minecraft.Block;
import net.minecraft.World;

import java.util.Random;

/** Controls extra ore restrictions for the upper inner Underworld. */
public final class UnderworldOreGateService {
    private static final int MIN_RESTRICTED_Y = 121;

    private UnderworldOreGateService() {
    }

    public static boolean shouldCancelOrePlacement(World world, Random random, int x, int y, int z, int blockId) {
        if (!world.isUnderworld() || y < MIN_RESTRICTED_Y) {
            return false;
        }

        int maxAbsXZ = WaitUpConfig.getUnderworldHighOreMaxAbsXZ();
        if (maxAbsXZ <= 0 || Math.abs(x) > maxAbsXZ || Math.abs(z) > maxAbsXZ) {
            return false;
        }

        double multiplier = getMultiplier(blockId);
        if (multiplier >= 1.0D) {
            return false;
        }

        if (multiplier <= 0.0D) {
            return true;
        }

        return random.nextDouble() >= multiplier;
    }

    private static double getMultiplier(int blockId) {
        if (blockId == Block.oreMithril.blockID) {
            return WaitUpConfig.getUnderworldHighMithrilMultiplier();
        }
        if (blockId == Block.oreDiamond.blockID) {
            return WaitUpConfig.getUnderworldHighDiamondMultiplier();
        }
        return 1.0D;
    }
}
