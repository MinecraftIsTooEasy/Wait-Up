package com.github.hahahha.waitup.mixins;

import com.github.hahahha.waitup.compat.ITFRBCompat;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

/** Applies optional compatibility mixins only when their target mod is present. */
public class WaitUpMixinPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return switch (mixinClassName) {
            case "com.github.hahahha.waitup.mixins.MixinITFRBWorldGenUnderworldCastle" ->
                    isClassPresent(ITFRBCompat.LICH_CASTLE_CLASS);
            case "com.github.hahahha.waitup.mixins.MixinITFRBContainerEnchantReserver" ->
                    isClassPresent(ITFRBCompat.ENCHANT_RESERVER_CONTAINER_CLASS);
            case "com.github.hahahha.waitup.mixins.MixinITFRBTileEntityEnchantReserver" ->
                    isClassPresent(ITFRBCompat.ENCHANT_RESERVER_TILE_CLASS);
            case "com.github.hahahha.waitup.mixins.MixinITFRBEnchantReserverInputSlot" ->
                    isClassPresent(ITFRBCompat.ENCHANT_RESERVER_INPUT_SLOT_CLASS);
            default -> true;
        };
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    private static boolean isClassPresent(String className) {
        String resourcePath = className.replace('.', '/') + ".class";
        ClassLoader classLoader = WaitUpMixinPlugin.class.getClassLoader();
        return classLoader != null && classLoader.getResource(resourcePath) != null;
    }
}
