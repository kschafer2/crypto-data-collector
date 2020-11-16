package com.protonmail.kschay.cryptotrader.step.execute

import com.fasterxml.jackson.databind.ObjectMapper
import com.protonmail.kschay.cryptotrader.domain.balance.Balance
import com.protonmail.kschay.cryptotrader.domain.balance.BalanceClient
import com.protonmail.kschay.cryptotrader.domain.execute.ExecuteOrder
import com.protonmail.kschay.cryptotrader.domain.fee.Fee
import com.protonmail.kschay.cryptotrader.domain.fee.FeeClient
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiPrivateClient
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiProperties
import com.protonmail.kschay.cryptotrader.domain.order.Order
import com.protonmail.kschay.cryptotrader.domain.order.OrderClient
import com.protonmail.kschay.cryptotrader.domain.payload.Payload
import com.protonmail.kschay.cryptotrader.domain.payload.PlaceOrderPayload
import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol
import com.protonmail.kschay.cryptotrader.domain.ticker.SimpleTicker
import com.protonmail.kschay.cryptotrader.domain.ticker.SimpleTickerClient
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

import javax.crypto.Mac

import static com.protonmail.kschay.cryptotrader.domain.gemini.GeminiEndpoints.*

@Component
class ExecuteStepClient extends GeminiPrivateClient
        implements BalanceClient, FeeClient, OrderClient, SimpleTickerClient {

    ExecuteStepClient(GeminiProperties geminiProperties,
                      WebClient webClient,
                      ObjectMapper objectMapper,
                      Mac mac) {
        super(geminiProperties, webClient, objectMapper, mac)
    }

    @Override
    List<Balance> getBalances() {
        return postForFlux(new Payload(GET_AVAILABLE_BALANCES.uri()), Balance.class) as List<Balance>
    }

    @Override
    Fee getFee() {
        return postForMono(new Payload(GET_NOTIONAL_VOLUME.uri()), Fee.class) as Fee
    }

    @Override
    Order placeOrder(ExecuteOrder executeOrder) {
        return postForMono(new PlaceOrderPayload(executeOrder), Order.class) as Order
    }

    @Override
    SimpleTicker getSimpleTicker(Symbol symbol) {
        return getForMono(GET_SIMPLE_TICKER.uri() + symbol, SimpleTicker.class) as SimpleTicker
    }
}
