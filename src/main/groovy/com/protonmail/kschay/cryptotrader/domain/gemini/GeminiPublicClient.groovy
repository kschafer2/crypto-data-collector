package com.protonmail.kschay.cryptotrader.domain.gemini

import com.fasterxml.jackson.databind.ObjectMapper
import com.protonmail.kschay.cryptotrader.domain.log.Logging
import org.springframework.web.reactive.function.client.WebClient
import reactor.util.retry.Retry

abstract class GeminiPublicClient extends Logging {

    protected final GeminiProperties geminiProperties
    protected final WebClient webClient
    protected final ObjectMapper objectMapper

    GeminiPublicClient(GeminiProperties geminiProperties,
                       WebClient webClient,
                       ObjectMapper objectMapper) {
        this.geminiProperties = geminiProperties
        this.webClient = webClient
        this.objectMapper = objectMapper
    }

    protected def getForMono(final String uri, final Class clazz) {
        def response = get(uri)
                .bodyToMono(clazz)
                .retryWhen(Retry.indefinitely())
                .doOnError(RuntimeException.class, { e -> log.error(e.getMessage())})
                .block()

        log.info("Response " + clazz.getSimpleName() + ": " + objectMapper.writeValueAsString(response))
        return response
    }

    private WebClient.ResponseSpec get(final String uri) {
        Thread.sleep(1000)

        log.info("Request: " + uri)

        return webClient.get()
                .uri(geminiProperties.baseUrl + uri)
                .retrieve()
    }
}
