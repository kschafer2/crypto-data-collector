package com.protonmail.kschay.cryptotrader.domain.payload

import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol

import static com.protonmail.kschay.cryptotrader.domain.gemini.GeminiEndpoints.GET_PAST_TRADES

class PastTradesPayload extends Payload {

    Symbol symbol
    Long timestamp

    PastTradesPayload(Symbol symbol, Long timestamp) {
        super(GET_PAST_TRADES.uri())
        this.symbol = symbol
        this.timestamp = timestamp
    }
}
