package com.github.hahahha.waitup.localization;

import moddedmite.rustedironcore.localization.LocalizationEnum;

/** Translation keys retained for ManyLib-related text resources. */
public enum WaitUpLocalization implements LocalizationEnum {
    MANYLIB_LOCAL_ONLY("message.wait_up.manylib.local_only");

    private final String key;

    WaitUpLocalization(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
