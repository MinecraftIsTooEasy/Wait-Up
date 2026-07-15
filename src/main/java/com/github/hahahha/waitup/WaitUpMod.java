package com.github.hahahha.waitup;

import com.github.hahahha.waitup.compat.ManyLibCompat;
import com.github.hahahha.waitup.config.WaitUpConfig;
import moddedmite.rustedironcore.api.util.FabricUtil;
import net.fabricmc.api.ModInitializer;
import net.xiaoyu233.fml.ModResourceManager;
import net.xiaoyu233.fml.config.ConfigRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/** Entrypoint for the Wait Up mod. */
public class WaitUpMod implements ModInitializer {
    public static final String MOD_ID = "wait_up";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public Optional<ConfigRegistry> createConfig() {
        return Optional.of(WaitUpConfig.INSTANCE);
    }

    @Override
    public void onInitialize() {
        ModResourceManager.addResourcePackDomain(MOD_ID);

        if (FabricUtil.isModLoaded("many-lib")) {
            ManyLibCompat.register();
        }
    }
}
