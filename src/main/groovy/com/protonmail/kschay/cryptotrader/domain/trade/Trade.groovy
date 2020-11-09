package com.protonmail.kschay.cryptotrader.domain.trade

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.protonmail.kschay.cryptotrader.domain.side.Side
import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol

import static com.protonmail.kschay.cryptotrader.domain.side.Side.sideOf
import static com.protonmail.kschay.cryptotrader.domain.symbol.Symbol.symbolOf

@JsonIgnoreProperties(ignoreUnknown = true)
class Trade {
    String symbol
    String price
    String amount
    Long timestamp
    @JsonProperty("timestampms")
    Long timestampMs
    String side
    @JsonProperty("fee_currency")
    Currency feeCurrency
    @JsonProperty("fee_amount")
    String feeAmount
    @JsonProperty("tid")
    String tradeId

    Trade(){}

    Trade(Symbol symbol, Side side) {
        this.symbol = symbol.toString()
        this.side = side.toString()
    }

    Symbol getSymbol() {
        return symbolOf(symbol)
    }

    Side getSide() {
        return sideOf(side)
    }
}
