package com.protonmail.kschay.cryptotrader.domain.ticker

import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol

interface TickerClient {
    Ticker getTicker(Symbol symbol)
}