package com.protonmail.kschay.cryptotrader.step.close

import com.fasterxml.jackson.databind.ObjectMapper
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiProperties
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiPublicClient
import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol
import com.protonmail.kschay.cryptotrader.domain.ticker.Ticker
import com.protonmail.kschay.cryptotrader.domain.ticker.TickerClient
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

import static com.protonmail.kschay.cryptotrader.domain.gemini.GeminiEndpoints.GET_TICKER

@Component
class GeminiTickerClient extends GeminiPublicClient implements TickerClient {

    GeminiTickerClient(GeminiProperties geminiProperties,
                       WebClient webClient,
                       ObjectMapper objectMapper) {
        super(geminiProperties, webClient, objectMapper)
    }

    @Override
    Ticker getTicker(final Symbol symbol) {
        return getForMono(GET_TICKER.uri() + symbol, Ticker.class) as Ticker
    }
}
