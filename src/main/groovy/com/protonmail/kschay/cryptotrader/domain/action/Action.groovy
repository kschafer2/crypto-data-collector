package com.protonmail.kschay.cryptotrader.domain.action

import com.protonmail.kschay.cryptotrader.domain.strength.Strength

enum Action {
    TO_USD, TO_BTC, FROM_USD, FROM_BTC, NONE

    static Action of(Strength previous, Strength strength) {
        if(previous.isStrong()) {
            if(strength.isStrong()) return NONE
            return TO_USD
        }
        if(strength.isStrong()) return FROM_USD
        return NONE
    }

    static Action of(Strength previousAlt, Strength alt, Strength previousBtc, Strength btc) {
        if(previousAlt.isStrong()) {
            if(alt.isStrong()) return NONE
            if(btc.isStrong()) return TO_BTC
            return TO_USD
        }
        if(previousBtc.isStrong()) {
            if(alt.isStrong()) return FROM_BTC
            return NONE
        }
        if(alt.isStrong()) return FROM_USD
        return NONE
    }
}
