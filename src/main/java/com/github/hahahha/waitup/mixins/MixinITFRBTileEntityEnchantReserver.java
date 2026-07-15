package com.github.hahahha.waitup.mixins;

import com.github.hahahha.waitup.compat.ITFRBCompat;
import com.github.hahahha.waitup.gate.QuartzSacrificeService;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.oilcake.mitelros.block.entity.TileEntityEnchantReserver")
public class MixinITFRBTileEntityEnchantReserver {
    @org.spongepowered.asm.mixin.Shadow
    public EntityPlayer player;

    @Inject(method = "isItemValidForSlot(ILnet/minecraft/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    private void waitUp$blockQuartzInputValidation(int slot, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (slot == 0 && shouldBlockQuartz(itemStack)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "checkInput(Lnet/minecraft/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    private void waitUp$blockQuartzConversionFallback(ItemStack inputStack, CallbackInfo ci) {
        if (shouldBlockQuartz(inputStack)) {
            if (this.player != null) {
                QuartzSacrificeService.sendItfrbReserverLockedMessage(this.player);
            }
            this.player = null;
            ci.cancel();
        }
    }

    @Inject(method = "closeChest()V", at = @At("TAIL"))
    private void waitUp$clearCurrentPlayerOnClose(CallbackInfo ci) {
        this.player = null;
    }

    private static boolean shouldBlockQuartz(ItemStack itemStack) {
        return ITFRBCompat.isCompatEnabled() && QuartzSacrificeService.shouldBlockNetherQuartzSacrifice(itemStack);
    }
}
