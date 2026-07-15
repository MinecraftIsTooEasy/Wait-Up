package com.github.hahahha.waitup.localization;

import net.minecraft.World;

import java.util.Locale;

/** Server-side plain text used for gameplay rule messages so clients don't need local mod lang files. */
public final class WaitUpServerText {
    private WaitUpServerText() {
    }

    public static String loginHeader() {
        return "[Wait Up] 当前存档配置:";
    }

    public static String loginDimensionDays(int underworldDay, int netherDay, int endDay) {
        return String.format(Locale.ROOT, "维度解锁天数: 地下世界 %d, 地狱 %d, 末地 %d。", underworldDay, netherDay, endDay);
    }

    public static String loginQuartzDay(int quartzDay) {
        return String.format(Locale.ROOT, "下界石英献祭解锁天数: %d。", quartzDay);
    }

    public static String loginGemXp(int quartzXp, int emeraldXp, int diamondXp, int lapisXp) {
        return String.format(Locale.ROOT, "献祭经验: 下界石英 %d, 绿宝石 %d, 钻石 %d, 青金石 %d。", quartzXp, emeraldXp, diamondXp, lapisXp);
    }

    public static String loginOreDisabled() {
        return "地下世界上层矿物调整: 已关闭。";
    }

    public static String loginOreConfig(int radius, double mithrilMultiplier, double diamondMultiplier) {
        return String.format(Locale.ROOT, "地下世界上层矿物调整: 半径 %d, 秘银 x%.2f, 钻石 x%.2f。", radius, mithrilMultiplier, diamondMultiplier);
    }

    public static String loginItfrbCompatDisabled() {
        return "ITF Reborn 兼容: 已关闭。";
    }

    public static String loginItfrbDistanceDisabled() {
        return "ITF Reborn 巫妖城堡中心保护区: 已关闭。";
    }

    public static String loginItfrbDistanceConfig(int protectedAbsXZ) {
        return String.format(Locale.ROOT, "ITF Reborn 巫妖城堡中心保护区: 当 |x| 和 |z| 同时不超过 %d 时禁止生成。", protectedAbsXZ);
    }

    public static String underworldLocked(int requiredDay, int currentDay) {
        return String.format(Locale.ROOT, "你必须到这个世界的第 %d 天后才能进入地下世界。当前是第 %d 天。", requiredDay, currentDay);
    }

    public static String otherDimensionLocked(int dimensionId, int requiredDay, int currentDay) {
        return String.format(Locale.ROOT, "%s要到这个世界的第 %d 天后才能进入。当前是第 %d 天。", getDimensionName(dimensionId), requiredDay, currentDay);
    }

    public static String underworldPortalReturnedToSpawn() {
        return "这座符文门尚未稳定，只会把你送回主世界出生点。";
    }

    public static String otherPortalReturnedToSpawn() {
        return "传送门拒绝了你，并把你送回主世界出生点。";
    }

    public static String netherQuartzSacrificeLocked(int requiredDay, int currentDay) {
        return String.format(Locale.ROOT, "你必须到这个世界的第 %d 天后才能献祭下界石英。当前是第 %d 天。", requiredDay, currentDay);
    }

    public static String netherQuartzItfrbReserverLocked(int requiredDay, int currentDay) {
        return String.format(Locale.ROOT, "你必须到这个世界的第 %d 天后才能把下界石英放入 ITF Reborn 的附魔回流底座。当前是第 %d 天。", requiredDay, currentDay);
    }

    public static String netherQuartzItfrbAbsorbLocked(int requiredDay, int currentDay) {
        return String.format(Locale.ROOT, "你必须到这个世界的第 %d 天后才能用 ITF Reborn 的汲取附魔把下界石英矿直接转成经验。当前是第 %d 天。", requiredDay, currentDay);
    }

    private static String getDimensionName(int dimensionId) {
        return switch (dimensionId) {
            case World.DIMENSION_ID_UNDERWORLD -> "地下世界";
            case World.DIMENSION_ID_NETHER -> "地狱";
            case World.DIMENSION_ID_THE_END -> "末地";
            default -> "该维度";
        };
    }
}
