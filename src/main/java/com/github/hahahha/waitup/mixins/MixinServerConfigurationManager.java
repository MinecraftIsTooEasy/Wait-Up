package com.github.hahahha.waitup.mixins;

import com.github.hahahha.waitup.gate.LoginNoticeService;
import net.minecraft.ServerConfigurationManager;
import net.minecraft.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerConfigurationManager.class)
public class MixinServerConfigurationManager {
    @Inject(method = "playerLoggedIn", at = @At("TAIL"))
    private void waitUp$sendConfigSummaryOnLogin(ServerPlayer player, CallbackInfo ci) {
        LoginNoticeService.sendConfigSummary(player);
    }
}
