package com.protonmail.kschay.cryptodatacollector.step.close

import com.fasterxml.jackson.databind.ObjectMapper
import com.protonmail.kschay.cryptodatacollector.domain.GeminiClient
import com.protonmail.kschay.cryptodatacollector.domain.Symbol
import com.protonmail.kschay.cryptodatacollector.domain.Ticker
import com.protonmail.kschay.cryptodatacollector.domain.TickerClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class GeminiTickerClient extends GeminiClient implements TickerClient {

    @Value('${gemini-exchange-service.url}')
    private String geminiExchangeServiceUrl

    GeminiTickerClient(WebClient webClient,
                       ObjectMapper objectMapper) {
        super(webClient, objectMapper)
    }

    @Override
    Ticker getTicker(final Symbol symbol) {
        return getForMono(geminiExchangeServiceUrl + "/ticker/" + symbol, Ticker.class) as Ticker
    }
}
