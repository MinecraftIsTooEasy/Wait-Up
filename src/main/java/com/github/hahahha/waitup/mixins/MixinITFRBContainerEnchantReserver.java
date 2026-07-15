package com.github.hahahha.waitup.mixins;

import com.github.hahahha.waitup.compat.ITFRBCompat;
import com.github.hahahha.waitup.gate.QuartzSacrificeService;
import net.minecraft.Container;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;
import net.minecraft.Slot;
import net.oilcake.mitelros.block.entity.TileEntityEnchantReserver;
import net.oilcake.mitelros.inventory.EnchantReserverInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Pseudo
@Mixin(targets = "net.oilcake.mitelros.container.ContainerEnchantReserver")
public abstract class MixinITFRBContainerEnchantReserver {
    @Shadow
    @Final
    @Nullable
    private TileEntityEnchantReserver tileEntityEnchantReserver;

    @Shadow
    @Final
    private EnchantReserverInventory inventory;

    @Inject(
            method = "<init>(Lnet/oilcake/mitelros/inventory/EnchantReserverInventory;Lnet/minecraft/EntityPlayer;III)V",
            at = @At("TAIL")
    )
    private void waitUp$bindCurrentPlayer(EnchantReserverInventory inventory, EntityPlayer player, int x, int y, int z, CallbackInfo ci) {
        if (ITFRBCompat.isCompatEnabled() && this.tileEntityEnchantReserver != null) {
            this.tileEntityEnchantReserver.player = player;
        }
    }

    @Inject(method = "transferStackInSlot(Lnet/minecraft/EntityPlayer;I)Lnet/minecraft/ItemStack;", at = @At("HEAD"), cancellable = true)
    private void waitUp$blockQuartzShiftClick(EntityPlayer player, int index, CallbackInfoReturnable<ItemStack> cir) {
        if (!ITFRBCompat.isCompatEnabled() || index < this.inventory.getSize()) {
            return;
        }

        Slot clickedSlot = ((Container) (Object) this).getSlot(index);
        if (clickedSlot != null
                && clickedSlot.getHasStack()
                && QuartzSacrificeService.shouldBlockNetherQuartzSacrifice(clickedSlot.getStack())) {
            QuartzSacrificeService.sendItfrbReserverLockedMessage(player);
            cir.setReturnValue(null);
        }
    }

    @Inject(method = "onContainerClosed(Lnet/minecraft/EntityPlayer;)V", at = @At("TAIL"))
    private void waitUp$clearCurrentPlayer(EntityPlayer player, CallbackInfo ci) {
        if (this.tileEntityEnchantReserver != null && this.tileEntityEnchantReserver.player == player) {
            this.tileEntityEnchantReserver.player = null;
        }
    }
}
