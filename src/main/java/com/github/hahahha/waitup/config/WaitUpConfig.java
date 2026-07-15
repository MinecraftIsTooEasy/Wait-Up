package com.github.hahahha.waitup.config;

import net.xiaoyu233.fml.config.Codec;
import net.xiaoyu233.fml.config.ConfigEntry;
import net.xiaoyu233.fml.config.ConfigRegistry;
import net.xiaoyu233.fml.config.ConfigRoot;
import net.xiaoyu233.fml.util.FieldReference;

import java.io.File;

/** Config values used by Wait Up gameplay restrictions. */
public final class WaitUpConfig {
    public static final FieldReference<Integer> UNDERWORLD_MIN_DAY = new FieldReference<>(0);
    public static final FieldReference<Integer> NETHER_MIN_DAY = new FieldReference<>(0);
    public static final FieldReference<Integer> END_MIN_DAY = new FieldReference<>(0);
    public static final FieldReference<Integer> NETHER_QUARTZ_SACRIFICE_MIN_DAY = new FieldReference<>(0);
    public static final FieldReference<Integer> NETHER_QUARTZ_SACRIFICE_XP = new FieldReference<>(50);
    public static final FieldReference<Integer> EMERALD_SACRIFICE_XP = new FieldReference<>(250);
    public static final FieldReference<Integer> DIAMOND_SACRIFICE_XP = new FieldReference<>(500);
    public static final FieldReference<Integer> LAPIS_SACRIFICE_XP = new FieldReference<>(25);
    public static final FieldReference<Integer> UNDERWORLD_HIGH_ORE_MAX_ABS_XZ = new FieldReference<>(0);
    public static final FieldReference<Double> UNDERWORLD_HIGH_MITHRIL_MULTIPLIER = new FieldReference<>(1.0D);
    public static final FieldReference<Double> UNDERWORLD_HIGH_DIAMOND_MULTIPLIER = new FieldReference<>(1.0D);
    public static final FieldReference<Boolean> ITFRB_COMPAT_ENABLED = new FieldReference<>(true);
    public static final FieldReference<Integer> ITFRB_LICH_CASTLE_MIN_DISTANCE = new FieldReference<>(0);

    public static final ConfigRoot ROOT = ConfigRoot.create(1)
            .withComment("Wait Up settings. Set a value to 0 to disable that restriction when applicable.")
            .addEntry(ConfigEntry.of("underworldMinDay", UNDERWORLD_MIN_DAY).withComment("Minimum Overworld day required to enter the Underworld. 0 disables the lock."))
            .addEntry(ConfigEntry.of("netherMinDay", NETHER_MIN_DAY).withComment("Minimum Overworld day required to enter the Nether. 0 disables the lock."))
            .addEntry(ConfigEntry.of("endMinDay", END_MIN_DAY).withComment("Minimum Overworld day required to enter the End. 0 disables the lock."))
            .addEntry(ConfigEntry.of("netherQuartzSacrificeMinDay", NETHER_QUARTZ_SACRIFICE_MIN_DAY).withComment("Minimum Overworld day required before Nether Quartz can be sacrificed for experience. 0 disables the lock."))
            .addEntry(ConfigEntry.of("netherQuartzSacrificeXp", NETHER_QUARTZ_SACRIFICE_XP).withComment("Experience granted when sacrificing one Nether Quartz after the day lock allows it. Vanilla is 50."))
            .addEntry(ConfigEntry.of("emeraldSacrificeXp", EMERALD_SACRIFICE_XP).withComment("Experience granted when sacrificing one Emerald. Vanilla is 250."))
            .addEntry(ConfigEntry.of("diamondSacrificeXp", DIAMOND_SACRIFICE_XP).withComment("Experience granted when sacrificing one Diamond. Vanilla is 500."))
            .addEntry(ConfigEntry.of("lapisSacrificeXp", LAPIS_SACRIFICE_XP).withComment("Experience granted when sacrificing one Lapis Lazuli dye item. Vanilla is 25."))
            .addEntry(ConfigEntry.of("underworldHighOreMaxAbsXZ", UNDERWORLD_HIGH_ORE_MAX_ABS_XZ).withComment("In the Underworld, ore adjustment only applies when both |x| and |z| are within this value and y is above 120. 0 disables this feature."))
            .addEntry(new ConfigEntry<>("underworldHighMithrilMultiplier", Codec.DOUBLE, UNDERWORLD_HIGH_MITHRIL_MULTIPLIER).withComment("Multiplier applied to mithril generation attempts in the configured high Underworld region. Valid range: 0.0 to 1.0. 1.0 keeps vanilla frequency."))
            .addEntry(new ConfigEntry<>("underworldHighDiamondMultiplier", Codec.DOUBLE, UNDERWORLD_HIGH_DIAMOND_MULTIPLIER).withComment("Multiplier applied to diamond generation attempts in the configured high Underworld region. Valid range: 0.0 to 1.0. 1.0 keeps vanilla frequency."))
            .addEntry(new ConfigEntry<>("itfrbCompatEnabled", Codec.BOOLEAN, ITFRB_COMPAT_ENABLED).withComment("Enables optional compatibility behavior when ITF Reborn is present."))
            .addEntry(ConfigEntry.of("itfrbLichCastleMinDistance", ITFRB_LICH_CASTLE_MIN_DISTANCE).withComment("Legacy key name kept for compatibility. When ITF Reborn compatibility is enabled, natural Lich Castles are blocked while both |x| and |z| are within this value. 0 disables the protected central region."));

    public static final ConfigRegistry INSTANCE = new ConfigRegistry(ROOT, new File("wait_up.json"));

    private WaitUpConfig() {
    }

    public static int getUnderworldMinDay() {
        return clampMin(UNDERWORLD_MIN_DAY.get(), 0);
    }

    public static int getNetherMinDay() {
        return clampMin(NETHER_MIN_DAY.get(), 0);
    }

    public static int getEndMinDay() {
        return clampMin(END_MIN_DAY.get(), 0);
    }

    public static int getNetherQuartzSacrificeMinDay() {
        return clampMin(NETHER_QUARTZ_SACRIFICE_MIN_DAY.get(), 0);
    }

    public static int getNetherQuartzSacrificeXp() {
        return clampMin(NETHER_QUARTZ_SACRIFICE_XP.get(), 0);
    }

    public static int getEmeraldSacrificeXp() {
        return clampMin(EMERALD_SACRIFICE_XP.get(), 0);
    }

    public static int getDiamondSacrificeXp() {
        return clampMin(DIAMOND_SACRIFICE_XP.get(), 0);
    }

    public static int getLapisSacrificeXp() {
        return clampMin(LAPIS_SACRIFICE_XP.get(), 0);
    }

    public static int getUnderworldHighOreMaxAbsXZ() {
        return clampMin(UNDERWORLD_HIGH_ORE_MAX_ABS_XZ.get(), 0);
    }

    public static double getUnderworldHighMithrilMultiplier() {
        return clamp(UNDERWORLD_HIGH_MITHRIL_MULTIPLIER.get(), 0.0D, 1.0D);
    }

    public static double getUnderworldHighDiamondMultiplier() {
        return clamp(UNDERWORLD_HIGH_DIAMOND_MULTIPLIER.get(), 0.0D, 1.0D);
    }

    public static boolean isItfrbCompatEnabled() {
        return ITFRB_COMPAT_ENABLED.get();
    }

    public static int getItfrbLichCastleProtectedAbsXZ() {
        return clampMin(ITFRB_LICH_CASTLE_MIN_DISTANCE.get(), 0);
    }

    public static int getItfrbLichCastleMinDistance() {
        return getItfrbLichCastleProtectedAbsXZ();
    }

    private static int clampMin(int value, int min) {
        return Math.max(value, min);
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
