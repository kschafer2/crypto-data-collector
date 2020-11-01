package com.protonmail.kschay.cryptotrader.domain.gemini

import com.protonmail.kschay.cryptotrader.domain.log.Logging
import org.springframework.web.reactive.function.client.WebClient

abstract class GeminiPublicClient extends Logging {

    private final GeminiProperties geminiProperties
    private final WebClient webClient

    GeminiPublicClient(GeminiProperties geminiProperties,
                       WebClient webClient) {
        this.geminiProperties = geminiProperties
        this.webClient = webClient
    }

    protected WebClient.ResponseSpec get(String uri) {
        Thread.sleep(1000)

        return webClient
                .get()
                .uri(geminiProperties.baseUrl + uri)
                .retrieve()
    }
}
