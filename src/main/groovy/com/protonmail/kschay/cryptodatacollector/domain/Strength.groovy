package com.protonmail.kschay.cryptodatacollector.domain

enum Strength {
    STRONG, WEAK

    boolean isStrong() {
        return equals(STRONG)
    }
}