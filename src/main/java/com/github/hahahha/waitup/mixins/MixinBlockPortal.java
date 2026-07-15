package com.github.hahahha.waitup.mixins;

import com.github.hahahha.waitup.gate.DimensionGateService;
import net.minecraft.BlockPortal;
import net.minecraft.Entity;
import net.minecraft.ServerPlayer;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockPortal.class)
public class MixinBlockPortal {
    @Inject(method = "getPortalTypeBasedOnLocation", at = @At("RETURN"), cancellable = true)
    private void waitUp$turnLockedUnderworldPortalsIntoSpawnPortals(World world, int x, int y, int z, boolean testForRunegate, CallbackInfoReturnable<Integer> cir) {
        int destinationDimensionId = BlockPortal.getDestinationDimensionIdForMetadata(cir.getReturnValue());
        if (DimensionGateService.isOverworldBottomPortalLockedForUnderworld(world, destinationDimensionId)) {
            cir.setReturnValue(BlockPortal.DESTINATION_OVERWORLD);
        }
    }

    @Inject(method = "onEntityCollidedWithBlock", at = @At("HEAD"), cancellable = true)
    private void waitUp$redirectLockedDimensionPortals(World world, int x, int y, int z, Entity entity, CallbackInfo ci) {
        if (world.isRemote || !(entity instanceof ServerPlayer player)) {
            return;
        }
        if (player.is_runegate_teleporting || player.ridingEntity != null || player.riddenByEntity != null) {
            return;
        }

        BlockPortal portal = (BlockPortal) (Object) this;
        if (!portal.isTouchingBottomBedrock(world, x, y, z)) {
            return;
        }

        int metadata = world.getBlockMetadata(x, y, z);
        if (world.isOverworld()
                && portal.isPortalToUnderworld(metadata, true)
                && DimensionGateService.isDimensionLocked(World.DIMENSION_ID_UNDERWORLD)) {
            DimensionGateService.sendPlayerToOverworldSpawn(player, World.DIMENSION_ID_UNDERWORLD);
            ci.cancel();
            return;
        }

        if (world.isUnderworld()
                && portal.isPortalToNether(metadata, true)
                && DimensionGateService.isDimensionLocked(World.DIMENSION_ID_NETHER)) {
            DimensionGateService.sendPlayerToOverworldSpawn(player, World.DIMENSION_ID_NETHER);
            ci.cancel();
        }
    }
}
