package com.protonmail.kschay.cryptodatacollector.domain

import static com.protonmail.kschay.cryptodatacollector.domain.Strength.STRONG
import static com.protonmail.kschay.cryptodatacollector.domain.Strength.WEAK

class CloseEma {
    Close close
    Ema ema

    CloseEma(Close close, Ema ema) {
        this.close = close
        this.ema = ema
    }

    Strength determineStrength() {
        return close.price > ema.value ? STRONG : WEAK
    }
}
