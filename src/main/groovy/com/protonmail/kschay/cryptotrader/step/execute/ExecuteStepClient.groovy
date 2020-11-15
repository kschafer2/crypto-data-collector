package com.protonmail.kschay.cryptotrader.step.execute

import com.fasterxml.jackson.databind.ObjectMapper
import com.protonmail.kschay.cryptotrader.domain.balance.Balance
import com.protonmail.kschay.cryptotrader.domain.balance.BalanceClient
import com.protonmail.kschay.cryptotrader.domain.currency.Currency
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
import reactor.util.retry.Retry

import javax.crypto.Mac
import java.util.stream.Collectors

import static com.protonmail.kschay.cryptotrader.domain.gemini.GeminiEndpoints.GET_AVAILABLE_BALANCES
import static com.protonmail.kschay.cryptotrader.domain.gemini.GeminiEndpoints.GET_NOTIONAL_VOLUME
import static com.protonmail.kschay.cryptotrader.domain.side.Side.BUY
import static com.protonmail.kschay.cryptotrader.domain.side.Side.SELL

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
        return post(new Payload(GET_AVAILABLE_BALANCES.uri()))
                .bodyToFlux(Balance.class)
                .retryWhen(Retry.indefinitely())
                .toStream()
                .collect(Collectors.toList())
    }

    @Override
    Balance getCurrentBalance(Currency currency) {
        return getBalances().stream()
                .filter({ b -> (b.getCurrency() == currency) })
                .findFirst()
                .orElse(null)
    }

    @Override
    Fee getFee() {
        return post(new Payload(GET_NOTIONAL_VOLUME.uri()))
                .bodyToMono(Fee.class)
                .retryWhen(Retry.indefinitely())
                .block()
    }

    @Override
    void placeOrder(ExecuteOrder executeOrder) {
        Symbol symbol = executeOrder.getSymbol()

        if(executeOrder.getFee() == null) {
            executeOrder.setFee(getFee())
        }
        if(executeOrder.getSide() == BUY) {
            executeOrder.setPrice(getSimpleTicker(symbol).getAsk())
            executeOrder.setAmount(getCurrentBalance(symbol.quote()).getAvailable())
        }
        else if(executeOrder.getSide() == SELL) {
            executeOrder.setPrice(getSimpleTicker(symbol).getBid())
            executeOrder.setAmount(getCurrentBalance(symbol.base()).getAvailable())
        }
        else {
            return
        }
        if(executeOrder.amountIsBelowTradeMinimum()) {
            return
        }
        Order order = post(new PlaceOrderPayload(executeOrder))
                .bodyToMono(Order.class)
                .retryWhen(Retry.indefinitely())
                .block()

        log.info("Response body: " + objectMapper.writeValueAsString(order))
        placeOrder(executeOrder)
    }

    @Override
    SimpleTicker getSimpleTicker(Symbol symbol) {
        log.info("Retrieving simple ticker for " + symbol)

        return get("/v1/pubticker/" + symbol)
                .bodyToMono(SimpleTicker.class)
                .retryWhen(Retry.indefinitely())
                .block()
    }
}
