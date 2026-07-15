package com.github.hahahha.waitup.mixins;

import com.github.hahahha.waitup.compat.ITFRBCompat;
import com.github.hahahha.waitup.gate.QuartzSacrificeService;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;
import net.oilcake.mitelros.inventory.EnchantReserverInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.oilcake.mitelros.inventory.EnchantReserverInventory$1")
public class MixinITFRBEnchantReserverInputSlot {
    @Shadow
    @Final
    private EnchantReserverInventory this$0;

    @Inject(method = "isItemValid(Lnet/minecraft/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    private void waitUp$blockQuartzInput(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (ITFRBCompat.isCompatEnabled() && QuartzSacrificeService.shouldBlockNetherQuartzSacrifice(itemStack)) {
            EntityPlayer player = this.this$0.tileEntityEnchantReserver != null ? this.this$0.tileEntityEnchantReserver.player : null;
            if (player != null) {
                QuartzSacrificeService.sendItfrbReserverLockedMessage(player);
            }
            cir.setReturnValue(false);
        }
    }
}
