package com.protonmail.kschay.cryptotrader.step.execute

import com.fasterxml.jackson.databind.ObjectMapper
import com.protonmail.kschay.cryptotrader.domain.balance.Balance
import com.protonmail.kschay.cryptotrader.domain.balance.BalanceClient
import com.protonmail.kschay.cryptotrader.domain.currency.Currency
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiPrivateClient
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiProperties
import com.protonmail.kschay.cryptotrader.domain.payload.Payload
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException

import java.util.stream.Collectors

import static com.protonmail.kschay.cryptotrader.domain.gemini.GeminiEndpoints.GET_AVAILABLE_BALANCES

@Component
class GeminiBalanceClient extends GeminiPrivateClient implements BalanceClient {

    GeminiBalanceClient(ObjectMapper objectMapper,
                        GeminiProperties geminiProperties,
                        WebClient webClient) {
        super(objectMapper, geminiProperties, webClient)
    }

    @Retryable(value = WebClientResponseException.class, maxAttempts = 3, backoff = @Backoff(value = 1000L))
    @Override
    List<Balance> getBalances() {
        return post(new Payload(GET_AVAILABLE_BALANCES.uri()))
                .bodyToFlux(Balance.class)
                .toStream().collect(Collectors.toList())
    }

    @Override
    Balance getCurrentBalance(Currency currency) {
        return getBalances().stream()
                .filter({ b -> (b.getCurrency() == currency) })
                .findFirst()
                .orElse(null)
    }
}
