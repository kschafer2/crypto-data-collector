package com.protonmail.kschay.cryptotrader.domain.currency

import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol

import static com.protonmail.kschay.cryptotrader.domain.symbol.Symbol.*

enum Currency {
    USD(List.of(), BigDecimal.ZERO),
    BTC(List.of(BTCUSD), BigDecimal.valueOf(0.00001)),
    ETH(List.of(ETHUSD, ETHBTC), BigDecimal.valueOf(0.000001)),
    BCH(List.of(BCHUSD, BCHBTC), BigDecimal.valueOf(0.001)),
    LTC(List.of(LTCUSD, LTCBTC), BigDecimal.valueOf(0.01)),
    ZEC(List.of(ZECUSD, ZECBTC), BigDecimal.valueOf(0.001)),
    LINK(List.of(LINKUSD, LINKBTC), BigDecimal.valueOf(0.1)),
    BAT(List.of(BATUSD, BATBTC), BigDecimal.valueOf(1)),
    OXT(List.of(OXTUSD, OXTBTC), BigDecimal.valueOf(1))

    final List<Symbol> symbols
    final BigDecimal tradeMinimum

    Currency(final List<Symbol> symbols,
            final BigDecimal tradeMinimum) {
        this.symbols = symbols
        this.tradeMinimum = tradeMinimum
    }
}
