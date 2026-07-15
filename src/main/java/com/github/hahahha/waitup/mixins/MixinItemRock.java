package com.github.hahahha.waitup.mixins;

import com.github.hahahha.waitup.gate.GemSacrificeService;
import com.github.hahahha.waitup.gate.QuartzSacrificeService;
import net.minecraft.EntityPlayer;
import net.minecraft.Item;
import net.minecraft.ItemRock;
import net.minecraft.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRock.class)
public class MixinItemRock {
    @Inject(method = "getExperienceValueWhenSacrificed(Lnet/minecraft/ItemStack;)I", at = @At("HEAD"), cancellable = true)
    private static void waitUp$lockQuartzSacrificeExperience(ItemStack itemStack, CallbackInfoReturnable<Integer> cir) {
        if (!GemSacrificeService.usesConfiguredSacrificeExperience(itemStack)) {
            return;
        }

        if (QuartzSacrificeService.shouldZeroNetherQuartzSacrificeExperience(itemStack)) {
            cir.setReturnValue(0);
            return;
        }

        cir.setReturnValue(GemSacrificeService.getConfiguredSacrificeExperience(itemStack));
    }

    @Inject(method = "onItemRightClick(Lnet/minecraft/EntityPlayer;Lnet/minecraft/ItemStack;FZ)Z", at = @At("HEAD"), cancellable = true)
    private static void waitUp$blockEarlyQuartzSacrifice(EntityPlayer player, ItemStack itemStack, float partialTick, boolean ctrlIsDown, CallbackInfoReturnable<Boolean> cir) {
        if (itemStack != null && itemStack.getItem() == Item.netherQuartz && QuartzSacrificeService.shouldBlockNetherQuartzSacrifice(player)) {
            cir.setReturnValue(false);
        }
    }
}
