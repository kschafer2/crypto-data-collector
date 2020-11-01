package com.protonmail.kschay.cryptotrader.domain.symbol

import com.protonmail.kschay.cryptotrader.domain.currency.Currency

enum Symbol {
    BTCUSD,
    ETHUSD,
    ETHBTC,
    ZECUSD,
    ZECBTC,
    ZECETH,
    ZECBCH,
    ZECLTC,
    BCHUSD,
    BCHBTC,
    BCHETH,
    LTCUSD,
    LTCBTC,
    LTCETH,
    LTCBCH,
    BATUSD,
    DAIUSD,
    OXTUSD,
    BATBTC,
    DAIBTC,
    LINKBTC,
    OXTBTC,
    BATETH,
    DAIETH,
    LINKETH,
    OXTETH,
    LINKUSD

    Integer getPeriod() {
        if(secondCurrency() == Currency.BTC) {
            return 16
        }
        return 8
    }

    Currency firstCurrency() {
        final def symbolString = toString()

        if(symbolString.length() == 6) {
            return Currency.valueOf(symbolString.substring(0, 3))
        }
        else if(symbolString.length() == 7) {
            try {
                return Currency.valueOf(symbolString.substring(0, 4))
            } catch(IllegalArgumentException ignored) {
                return Currency.valueOf(symbolString.substring(0, 3))
            }
        }
        return null
    }

    Currency secondCurrency() {
        final def symbolString = toString()

        if(symbolString.length() == 6) {
            return Currency.valueOf(symbolString.substring(3))
        }
        else if(symbolString.length() == 7) {
            try {
                return Currency.valueOf(symbolString.substring(4))
            } catch(IllegalArgumentException ignored) {
                return Currency.valueOf(symbolString.substring(3))
            }
        }
        return null
    }

    static Symbol fromLowerCaseString(final String lowerCaseString) {
        return valueOf(lowerCaseString.toUpperCase())
    }

    static Symbol of(final Currency first, final Currency second) {
        return valueOf(first.toString() + second.toString())
    }
}