package com.protonmail.kschay.cryptotrader.domain.currency

import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol

import static com.protonmail.kschay.cryptotrader.domain.symbol.Symbol.*

enum Currency {
    USD,
    BTC(List.of(BTCUSD)),
    ETH(List.of(ETHUSD, ETHBTC)),
    BCH(List.of(BCHUSD, BCHBTC)),
    LTC(List.of(LTCUSD, LTCBTC)),
    ZEC(List.of(ZECUSD, ZECBTC)),
    LINK(List.of(LINKUSD, LINKBTC)),
    BAT(List.of(BATUSD, BATBTC)),
    OXT(List.of(OXTUSD, OXTBTC))

    private List<Symbol> symbols

    Currency(){}

    Currency(List<Symbol> symbols) {
        this.symbols = symbols
    }

    List<Symbol> symbols() {
        return symbols
    }
}
