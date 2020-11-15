package com.protonmail.kschay.cryptotrader.domain.gemini

import com.protonmail.kschay.cryptotrader.domain.log.Logging
import org.springframework.web.reactive.function.client.WebClient

abstract class GeminiPublicClient extends Logging {

    protected final GeminiProperties geminiProperties
    protected final WebClient webClient

    GeminiPublicClient(GeminiProperties geminiProperties,
                       WebClient webClient) {
        this.geminiProperties = geminiProperties
        this.webClient = webClient
    }

    protected WebClient.ResponseSpec get(final String uri) {
        Thread.sleep(1000)

        return webClient
                .get()
                .uri(geminiProperties.baseUrl + uri)
                .retrieve()
    }
}
