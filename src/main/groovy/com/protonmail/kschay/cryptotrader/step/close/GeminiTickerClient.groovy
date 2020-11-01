package com.protonmail.kschay.cryptotrader.step.close

import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiProperties
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiPublicClient
import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol
import com.protonmail.kschay.cryptotrader.domain.ticker.Ticker
import com.protonmail.kschay.cryptotrader.domain.ticker.TickerClient
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class GeminiTickerClient extends GeminiPublicClient implements TickerClient {

    GeminiTickerClient(GeminiProperties geminiProperties, WebClient webClient) {
        super(geminiProperties, webClient)
    }

    @Override
    @Retryable(maxAttempts = 24, backoff = @Backoff(value = 900000L))
    Ticker getTicker(Symbol symbol) {
        log.info("Retrieving ticker for " + symbol)

        return get("/v2/ticker/" + symbol)
                .bodyToMono(Ticker.class)
                .block()
    }
}
