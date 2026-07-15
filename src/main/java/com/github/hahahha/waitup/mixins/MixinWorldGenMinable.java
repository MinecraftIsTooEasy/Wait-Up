package com.github.hahahha.waitup.mixins;

import com.github.hahahha.waitup.gate.UnderworldOreGateService;
import net.minecraft.World;
import net.minecraft.WorldGenMinable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(WorldGenMinable.class)
public class MixinWorldGenMinable {
    @Shadow
    private int minableBlockId;

    @Inject(method = "growVein(Lnet/minecraft/World;Ljava/util/Random;IIIIZZ)I", at = @At("HEAD"), cancellable = true)
    private void waitUp$limitHighUnderworldOreFrequency(World world, Random rand, int blocksToGrow, int x, int y, int z, boolean mustBeSupported, boolean isDirt, CallbackInfoReturnable<Integer> cir) {
        if (UnderworldOreGateService.shouldCancelOrePlacement(world, rand, x, y, z, this.minableBlockId)) {
            cir.setReturnValue(0);
        }
    }
}
