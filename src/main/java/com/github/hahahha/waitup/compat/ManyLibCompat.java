package com.github.hahahha.waitup.compat;

import com.github.hahahha.waitup.WaitUpMod;
import com.github.hahahha.waitup.config.WaitUpConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.interfaces.IValueChangeCallback;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.config.options.ConfigToggle;
import moddedmite.rustedironcore.api.util.FabricUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

/** Optional ManyLib bridge for keeping Wait Up config compatible with ManyLib config handling. */
public final class ManyLibCompat {
    private static boolean registered;

    private ManyLibCompat() {
    }

    public static void register() {
        if (registered) {
            return;
        }

        WaitUpManyLibConfig.INSTANCE.load();
        ConfigManager.getInstance().registerConfig(WaitUpMod.MOD_ID, WaitUpManyLibConfig.INSTANCE);
        registered = true;
    }

    private static final class WaitUpManyLibConfig extends SimpleConfigs {
        private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        private static final File CONFIG_FILE = WaitUpConfig.INSTANCE.getPathToConfigFile();
        private static final ConfigInteger UNDERWORLD_MIN_DAY = new ConfigInteger(
                "wait_up.underworldMinDay", WaitUpConfig.getUnderworldMinDay(), 0, Integer.MAX_VALUE, false,
                "Minimum Overworld day required to enter the Underworld. 0 disables the lock."
        );
        private static final ConfigInteger NETHER_MIN_DAY = new ConfigInteger(
                "wait_up.netherMinDay", WaitUpConfig.getNetherMinDay(), 0, Integer.MAX_VALUE, false,
                "Minimum Overworld day required to enter the Nether. 0 disables the lock."
        );
        private static final ConfigInteger END_MIN_DAY = new ConfigInteger(
                "wait_up.endMinDay", WaitUpConfig.getEndMinDay(), 0, Integer.MAX_VALUE, false,
                "Minimum Overworld day required to enter the End. 0 disables the lock."
        );
        private static final ConfigInteger NETHER_QUARTZ_SACRIFICE_MIN_DAY = new ConfigInteger(
                "wait_up.netherQuartzSacrificeMinDay", WaitUpConfig.getNetherQuartzSacrificeMinDay(), 0, Integer.MAX_VALUE, false,
                "Minimum Overworld day required before Nether Quartz can be sacrificed for experience. 0 disables the lock."
        );
        private static final ConfigInteger NETHER_QUARTZ_SACRIFICE_XP = new ConfigInteger(
                "wait_up.netherQuartzSacrificeXp", WaitUpConfig.getNetherQuartzSacrificeXp(), 0, Integer.MAX_VALUE, false,
                "Experience granted when sacrificing one Nether Quartz after the day lock allows it. Vanilla is 50."
        );
        private static final ConfigInteger EMERALD_SACRIFICE_XP = new ConfigInteger(
                "wait_up.emeraldSacrificeXp", WaitUpConfig.getEmeraldSacrificeXp(), 0, Integer.MAX_VALUE, false,
                "Experience granted when sacrificing one Emerald. Vanilla is 250."
        );
        private static final ConfigInteger DIAMOND_SACRIFICE_XP = new ConfigInteger(
                "wait_up.diamondSacrificeXp", WaitUpConfig.getDiamondSacrificeXp(), 0, Integer.MAX_VALUE, false,
                "Experience granted when sacrificing one Diamond. Vanilla is 500."
        );
        private static final ConfigInteger LAPIS_SACRIFICE_XP = new ConfigInteger(
                "wait_up.lapisSacrificeXp", WaitUpConfig.getLapisSacrificeXp(), 0, Integer.MAX_VALUE, false,
                "Experience granted when sacrificing one Lapis Lazuli dye item. Vanilla is 25."
        );
        private static final ConfigInteger UNDERWORLD_HIGH_ORE_MAX_ABS_XZ = new ConfigInteger(
                "wait_up.underworldHighOreMaxAbsXZ", WaitUpConfig.getUnderworldHighOreMaxAbsXZ(), 0, Integer.MAX_VALUE, false,
                "Only affects the Underworld when both |x| and |z| are within this value and y is above 120. Set to 0 to disable this feature."
        );
        private static final ConfigDouble UNDERWORLD_HIGH_MITHRIL_MULTIPLIER = new ConfigDouble(
                "wait_up.underworldHighMithrilMultiplier", WaitUpConfig.getUnderworldHighMithrilMultiplier(), 0.0D, 1.0D, false,
                "Multiplier for mithril generation attempts in the configured high Underworld region. Enter a decimal such as 0.35. 1.0 keeps vanilla frequency."
        );
        private static final ConfigDouble UNDERWORLD_HIGH_DIAMOND_MULTIPLIER = new ConfigDouble(
                "wait_up.underworldHighDiamondMultiplier", WaitUpConfig.getUnderworldHighDiamondMultiplier(), 0.0D, 1.0D, false,
                "Multiplier for diamond generation attempts in the configured high Underworld region. Enter a decimal such as 0.35. 1.0 keeps vanilla frequency."
        );
        private static final ConfigToggle ITFRB_COMPAT_ENABLED = new ConfigToggle(
                "wait_up.itfrbCompatEnabled", "", WaitUpConfig.isItfrbCompatEnabled(),
                "Enable optional compatibility behavior with ITF Reborn."
        );
        private static final ConfigInteger ITFRB_LICH_CASTLE_MIN_DISTANCE = new ConfigInteger(
                "wait_up.itfrbLichCastleMinDistance", WaitUpConfig.getItfrbLichCastleProtectedAbsXZ(), 0, Integer.MAX_VALUE, false,
                "Legacy config id kept for compatibility. Natural Lich Castles are blocked while both |x| and |z| are within this value. Set to 0 to disable the protected central region."
        );
        private static final WaitUpManyLibConfig INSTANCE = new WaitUpManyLibConfig();

        private WaitUpManyLibConfig() {
            super(
                    WaitUpMod.MOD_ID,
                    List.of(),
                    List.of(
                            UNDERWORLD_MIN_DAY,
                            NETHER_MIN_DAY,
                            END_MIN_DAY,
                            NETHER_QUARTZ_SACRIFICE_MIN_DAY,
                            NETHER_QUARTZ_SACRIFICE_XP,
                            EMERALD_SACRIFICE_XP,
                            DIAMOND_SACRIFICE_XP,
                            LAPIS_SACRIFICE_XP,
                            UNDERWORLD_HIGH_ORE_MAX_ABS_XZ,
                            UNDERWORLD_HIGH_MITHRIL_MULTIPLIER,
                            UNDERWORLD_HIGH_DIAMOND_MULTIPLIER,
                            ITFRB_COMPAT_ENABLED,
                            ITFRB_LICH_CASTLE_MIN_DISTANCE
                    ),
                    "Edit portal day locks, Nether Quartz sacrifice day locks, upper Underworld ore multipliers, and optional ITF Reborn compatibility. Set a day lock or distance to 0 to disable that limit."
            );

            IValueChangeCallback<ConfigInteger> callback = config -> this.syncRuntimeValues();
            UNDERWORLD_MIN_DAY.setValueChangeCallback(callback);
            NETHER_MIN_DAY.setValueChangeCallback(callback);
            END_MIN_DAY.setValueChangeCallback(callback);
            NETHER_QUARTZ_SACRIFICE_MIN_DAY.setValueChangeCallback(callback);
            NETHER_QUARTZ_SACRIFICE_XP.setValueChangeCallback(callback);
            EMERALD_SACRIFICE_XP.setValueChangeCallback(callback);
            DIAMOND_SACRIFICE_XP.setValueChangeCallback(callback);
            LAPIS_SACRIFICE_XP.setValueChangeCallback(callback);
            UNDERWORLD_HIGH_ORE_MAX_ABS_XZ.setValueChangeCallback(callback);

            IValueChangeCallback<ConfigDouble> doubleCallback = config -> this.syncRuntimeValues();
            UNDERWORLD_HIGH_MITHRIL_MULTIPLIER.setValueChangeCallback(doubleCallback);
            UNDERWORLD_HIGH_DIAMOND_MULTIPLIER.setValueChangeCallback(doubleCallback);

            ITFRB_COMPAT_ENABLED.setValueChangeCallback(config -> this.syncRuntimeValues());
            ITFRB_LICH_CASTLE_MIN_DISTANCE.setValueChangeCallback(callback);
        }

        @Override
        public void load() {
            if (!CONFIG_FILE.exists()) {
                this.pullRuntimeValues();
                this.save();
                return;
            }

            try (Reader reader = new FileReader(CONFIG_FILE)) {
                JsonObject root = GSON.fromJson(reader, JsonObject.class);
                if (root != null) {
                    UNDERWORLD_MIN_DAY.setIntegerValue(readInt(root, "underworldMinDay", WaitUpConfig.getUnderworldMinDay()));
                    NETHER_MIN_DAY.setIntegerValue(readInt(root, "netherMinDay", WaitUpConfig.getNetherMinDay()));
                    END_MIN_DAY.setIntegerValue(readInt(root, "endMinDay", WaitUpConfig.getEndMinDay()));
                    NETHER_QUARTZ_SACRIFICE_MIN_DAY.setIntegerValue(readInt(root, "netherQuartzSacrificeMinDay", WaitUpConfig.getNetherQuartzSacrificeMinDay()));
                    NETHER_QUARTZ_SACRIFICE_XP.setIntegerValue(readInt(root, "netherQuartzSacrificeXp", WaitUpConfig.getNetherQuartzSacrificeXp()));
                    EMERALD_SACRIFICE_XP.setIntegerValue(readInt(root, "emeraldSacrificeXp", WaitUpConfig.getEmeraldSacrificeXp()));
                    DIAMOND_SACRIFICE_XP.setIntegerValue(readInt(root, "diamondSacrificeXp", WaitUpConfig.getDiamondSacrificeXp()));
                    LAPIS_SACRIFICE_XP.setIntegerValue(readInt(root, "lapisSacrificeXp", WaitUpConfig.getLapisSacrificeXp()));
                    UNDERWORLD_HIGH_ORE_MAX_ABS_XZ.setIntegerValue(readInt(root, "underworldHighOreMaxAbsXZ", WaitUpConfig.getUnderworldHighOreMaxAbsXZ()));
                    UNDERWORLD_HIGH_MITHRIL_MULTIPLIER.setDoubleValue(readDouble(root, "underworldHighMithrilMultiplier", WaitUpConfig.getUnderworldHighMithrilMultiplier()));
                    UNDERWORLD_HIGH_DIAMOND_MULTIPLIER.setDoubleValue(readDouble(root, "underworldHighDiamondMultiplier", WaitUpConfig.getUnderworldHighDiamondMultiplier()));
                    ITFRB_COMPAT_ENABLED.setBooleanValue(readBoolean(root, "itfrbCompatEnabled", WaitUpConfig.isItfrbCompatEnabled()));
                    ITFRB_LICH_CASTLE_MIN_DISTANCE.setIntegerValue(readInt(root, "itfrbLichCastleMinDistance", WaitUpConfig.getItfrbLichCastleProtectedAbsXZ()));
                } else {
                    this.pullRuntimeValues();
                }
            } catch (Exception e) {
                WaitUpMod.LOGGER.warn("Wait Up ManyLib config load failed for '{}', falling back to runtime values.", CONFIG_FILE.getAbsolutePath(), e);
                this.pullRuntimeValues();
            }

            this.syncRuntimeValues();
        }

        @Override
        public void save() {
            this.syncRuntimeValues();

            File parent = CONFIG_FILE.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }

            JsonObject root = new JsonObject();
            root.addProperty("underworldMinDay", UNDERWORLD_MIN_DAY.getIntegerValue());
            root.addProperty("netherMinDay", NETHER_MIN_DAY.getIntegerValue());
            root.addProperty("endMinDay", END_MIN_DAY.getIntegerValue());
            root.addProperty("netherQuartzSacrificeMinDay", NETHER_QUARTZ_SACRIFICE_MIN_DAY.getIntegerValue());
            root.addProperty("netherQuartzSacrificeXp", NETHER_QUARTZ_SACRIFICE_XP.getIntegerValue());
            root.addProperty("emeraldSacrificeXp", EMERALD_SACRIFICE_XP.getIntegerValue());
            root.addProperty("diamondSacrificeXp", DIAMOND_SACRIFICE_XP.getIntegerValue());
            root.addProperty("lapisSacrificeXp", LAPIS_SACRIFICE_XP.getIntegerValue());
            root.addProperty("underworldHighOreMaxAbsXZ", UNDERWORLD_HIGH_ORE_MAX_ABS_XZ.getIntegerValue());
            root.addProperty("underworldHighMithrilMultiplier", UNDERWORLD_HIGH_MITHRIL_MULTIPLIER.getDoubleValue());
            root.addProperty("underworldHighDiamondMultiplier", UNDERWORLD_HIGH_DIAMOND_MULTIPLIER.getDoubleValue());
            root.addProperty("itfrbCompatEnabled", ITFRB_COMPAT_ENABLED.getBooleanValue());
            root.addProperty("itfrbLichCastleMinDistance", ITFRB_LICH_CASTLE_MIN_DISTANCE.getIntegerValue());

            try (Writer writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(root, writer);
            } catch (IOException e) {
                WaitUpMod.LOGGER.warn("Wait Up ManyLib config save failed for '{}'.", CONFIG_FILE.getAbsolutePath(), e);
                if (!FabricUtil.isServer()) {
                    ManyLibClientFeedback.warnIfSaveFailed();
                }
            }

            if (!FabricUtil.isServer()) {
                ManyLibClientFeedback.warnIfRemoteServerConfigWillNotChange();
            }
        }

        private void pullRuntimeValues() {
            UNDERWORLD_MIN_DAY.setIntegerValue(WaitUpConfig.getUnderworldMinDay());
            NETHER_MIN_DAY.setIntegerValue(WaitUpConfig.getNetherMinDay());
            END_MIN_DAY.setIntegerValue(WaitUpConfig.getEndMinDay());
            NETHER_QUARTZ_SACRIFICE_MIN_DAY.setIntegerValue(WaitUpConfig.getNetherQuartzSacrificeMinDay());
            NETHER_QUARTZ_SACRIFICE_XP.setIntegerValue(WaitUpConfig.getNetherQuartzSacrificeXp());
            EMERALD_SACRIFICE_XP.setIntegerValue(WaitUpConfig.getEmeraldSacrificeXp());
            DIAMOND_SACRIFICE_XP.setIntegerValue(WaitUpConfig.getDiamondSacrificeXp());
            LAPIS_SACRIFICE_XP.setIntegerValue(WaitUpConfig.getLapisSacrificeXp());
            UNDERWORLD_HIGH_ORE_MAX_ABS_XZ.setIntegerValue(WaitUpConfig.getUnderworldHighOreMaxAbsXZ());
            UNDERWORLD_HIGH_MITHRIL_MULTIPLIER.setDoubleValue(WaitUpConfig.getUnderworldHighMithrilMultiplier());
            UNDERWORLD_HIGH_DIAMOND_MULTIPLIER.setDoubleValue(WaitUpConfig.getUnderworldHighDiamondMultiplier());
            ITFRB_COMPAT_ENABLED.setBooleanValue(WaitUpConfig.isItfrbCompatEnabled());
            ITFRB_LICH_CASTLE_MIN_DISTANCE.setIntegerValue(WaitUpConfig.getItfrbLichCastleProtectedAbsXZ());
        }

        private void syncRuntimeValues() {
            WaitUpConfig.UNDERWORLD_MIN_DAY.set(UNDERWORLD_MIN_DAY.getIntegerValue());
            WaitUpConfig.NETHER_MIN_DAY.set(NETHER_MIN_DAY.getIntegerValue());
            WaitUpConfig.END_MIN_DAY.set(END_MIN_DAY.getIntegerValue());
            WaitUpConfig.NETHER_QUARTZ_SACRIFICE_MIN_DAY.set(NETHER_QUARTZ_SACRIFICE_MIN_DAY.getIntegerValue());
            WaitUpConfig.NETHER_QUARTZ_SACRIFICE_XP.set(NETHER_QUARTZ_SACRIFICE_XP.getIntegerValue());
            WaitUpConfig.EMERALD_SACRIFICE_XP.set(EMERALD_SACRIFICE_XP.getIntegerValue());
            WaitUpConfig.DIAMOND_SACRIFICE_XP.set(DIAMOND_SACRIFICE_XP.getIntegerValue());
            WaitUpConfig.LAPIS_SACRIFICE_XP.set(LAPIS_SACRIFICE_XP.getIntegerValue());
            WaitUpConfig.UNDERWORLD_HIGH_ORE_MAX_ABS_XZ.set(UNDERWORLD_HIGH_ORE_MAX_ABS_XZ.getIntegerValue());
            WaitUpConfig.UNDERWORLD_HIGH_MITHRIL_MULTIPLIER.set(UNDERWORLD_HIGH_MITHRIL_MULTIPLIER.getDoubleValue());
            WaitUpConfig.UNDERWORLD_HIGH_DIAMOND_MULTIPLIER.set(UNDERWORLD_HIGH_DIAMOND_MULTIPLIER.getDoubleValue());
            WaitUpConfig.ITFRB_COMPAT_ENABLED.set(ITFRB_COMPAT_ENABLED.getBooleanValue());
            WaitUpConfig.ITFRB_LICH_CASTLE_MIN_DISTANCE.set(ITFRB_LICH_CASTLE_MIN_DISTANCE.getIntegerValue());
        }

        private static int readInt(JsonObject root, String key, int fallback) {
            if (!root.has(key) || !root.get(key).isJsonPrimitive()) {
                return fallback;
            }

            try {
                return root.get(key).getAsInt();
            } catch (Exception e) {
                WaitUpMod.LOGGER.warn("Wait Up ManyLib config key '{}' is not a valid integer, using fallback {}.", key, fallback, e);
                return fallback;
            }
        }

        private static double readDouble(JsonObject root, String key, double fallback) {
            if (!root.has(key) || !root.get(key).isJsonPrimitive()) {
                return fallback;
            }

            try {
                return root.get(key).getAsDouble();
            } catch (Exception e) {
                WaitUpMod.LOGGER.warn("Wait Up ManyLib config key '{}' is not a valid double, using fallback {}.", key, fallback, e);
                return fallback;
            }
        }

        private static boolean readBoolean(JsonObject root, String key, boolean fallback) {
            if (!root.has(key) || !root.get(key).isJsonPrimitive()) {
                return fallback;
            }

            try {
                return root.get(key).getAsBoolean();
            } catch (Exception e) {
                WaitUpMod.LOGGER.warn("Wait Up ManyLib config key '{}' is not a valid boolean, using fallback {}.", key, fallback, e);
                return fallback;
            }
        }
    }
}
