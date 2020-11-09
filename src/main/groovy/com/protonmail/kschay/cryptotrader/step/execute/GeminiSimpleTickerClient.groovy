package com.protonmail.kschay.cryptotrader.step.execute

import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiProperties
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiPublicClient
import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol
import com.protonmail.kschay.cryptotrader.domain.ticker.SimpleTicker
import com.protonmail.kschay.cryptotrader.domain.ticker.SimpleTickerClient
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class GeminiSimpleTickerClient extends GeminiPublicClient implements SimpleTickerClient {

    GeminiSimpleTickerClient(GeminiProperties geminiProperties, WebClient webClient) {
        super(geminiProperties, webClient)
    }

    @Override
    @Retryable(maxAttempts = 24, backoff = @Backoff(value = 900000L))
    SimpleTicker getSimpleTicker(Symbol symbol) {
        log.info("Retrieving simple ticker for " + symbol)

        return get("/v1/pubticker/" + symbol)
                .bodyToMono(SimpleTicker.class)
                .block()
    }
}
