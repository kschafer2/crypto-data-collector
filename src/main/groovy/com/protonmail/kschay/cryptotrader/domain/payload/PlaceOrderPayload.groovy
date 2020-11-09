package com.protonmail.kschay.cryptotrader.domain.payload

import com.protonmail.kschay.cryptotrader.domain.execute.ExecuteOrder
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiEndpoints
import com.protonmail.kschay.cryptotrader.domain.options.Options
import com.protonmail.kschay.cryptotrader.domain.side.Side
import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol
import com.protonmail.kschay.cryptotrader.domain.type.Type

class PlaceOrderPayload extends Payload {

    Symbol symbol
    String amount
    String price
    Side side
    String type
    Object[] options

    PlaceOrderPayload(ExecuteOrder executeOrder) {
        super(GeminiEndpoints.NEW_ORDER.uri())

        this.symbol = executeOrder.symbol
        this.amount = executeOrder.amount
        this.price = executeOrder.price
        this.side = executeOrder.side
        this.type = Type.EXCHANGE_LIMIT.displayName
        this.options = List.of(Options.IMMEDIATE_OR_CANCEL.displayName).toArray()
    }
}
