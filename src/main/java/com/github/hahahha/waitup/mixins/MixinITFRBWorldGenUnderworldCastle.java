package com.github.hahahha.waitup.mixins;

import com.github.hahahha.waitup.gate.ITFRBLichCastleGateService;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Pseudo
@Mixin(targets = "net.oilcake.mitelros.world.WorldGenUnderworldCastle")
public class MixinITFRBWorldGenUnderworldCastle {
    @Inject(method = "generate(Lnet/minecraft/World;Ljava/util/Random;III)Z", at = @At("HEAD"), cancellable = true)
    private void waitUp$blockNearbyLichCastleGeneration(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (ITFRBLichCastleGateService.shouldCancelNaturalGeneration(world, x, z)) {
            cir.setReturnValue(false);
        }
    }
}
