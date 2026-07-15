package com.github.hahahha.waitup.gate;

import com.github.hahahha.waitup.config.WaitUpConfig;
import net.minecraft.Item;
import net.minecraft.ItemStack;

/** Centralizes configurable experience values for sacrifice-able gems. */
public final class GemSacrificeService {
    private GemSacrificeService() {
    }

    public static int getConfiguredSacrificeExperience(ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        }

        Item item = itemStack.getItem();
        if (item == Item.netherQuartz) {
            return WaitUpConfig.getNetherQuartzSacrificeXp();
        }
        if (item == Item.emerald) {
            return WaitUpConfig.getEmeraldSacrificeXp();
        }
        if (item == Item.diamond) {
            return WaitUpConfig.getDiamondSacrificeXp();
        }
        if (item == Item.dyePowder && itemStack.getItemSubtype() == 4) {
            return WaitUpConfig.getLapisSacrificeXp();
        }

        return 0;
    }

    public static boolean usesConfiguredSacrificeExperience(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }

        Item item = itemStack.getItem();
        return item == Item.netherQuartz
                || item == Item.emerald
                || item == Item.diamond
                || item == Item.dyePowder && itemStack.getItemSubtype() == 4;
    }
}
