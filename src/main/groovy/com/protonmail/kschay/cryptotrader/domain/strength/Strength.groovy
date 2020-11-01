package com.protonmail.kschay.cryptotrader.domain.strength

enum Strength {
    STRONG, WEAK

    boolean isStrong() {
        return equals(STRONG)
    }
}