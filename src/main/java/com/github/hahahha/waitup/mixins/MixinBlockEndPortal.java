package com.github.hahahha.waitup.mixins;

import com.github.hahahha.waitup.gate.DimensionGateService;
import net.minecraft.BlockEndPortal;
import net.minecraft.Entity;
import net.minecraft.ServerPlayer;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEndPortal.class)
public class MixinBlockEndPortal {
    @Inject(method = "onEntityCollidedWithBlock", at = @At("HEAD"), cancellable = true)
    private void waitUp$redirectLockedEndPortals(World world, int x, int y, int z, Entity entity, CallbackInfo ci) {
        if (world.isRemote || world.isTheEnd() || !(entity instanceof ServerPlayer player)) {
            return;
        }
        if (player.is_runegate_teleporting || entity.ridingEntity != null || entity.riddenByEntity != null) {
            return;
        }
        if (!DimensionGateService.isDimensionLocked(World.DIMENSION_ID_THE_END)) {
            return;
        }

        DimensionGateService.sendPlayerToOverworldSpawn(player, World.DIMENSION_ID_THE_END);
        ci.cancel();
    }
}
