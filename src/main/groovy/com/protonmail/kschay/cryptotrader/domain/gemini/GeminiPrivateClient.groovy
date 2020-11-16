package com.protonmail.kschay.cryptotrader.domain.gemini

import com.fasterxml.jackson.databind.ObjectMapper
import com.protonmail.kschay.cryptotrader.domain.payload.Payload
import org.springframework.http.MediaType
import org.springframework.security.crypto.codec.Hex
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.util.retry.Retry

import javax.crypto.Mac
import java.util.stream.Collectors

abstract class GeminiPrivateClient extends GeminiPublicClient {

    private final Mac mac

    GeminiPrivateClient(GeminiProperties geminiProperties,
                        WebClient webClient,
                        ObjectMapper objectMapper,
                        Mac mac) {
        super(geminiProperties, webClient, objectMapper)
        this.mac = mac
    }

    protected def postForMono(final Payload payload, final Class clazz) {
        def response = post(payload)
                .bodyToMono(clazz)
                .retryWhen(Retry.indefinitely())
                .doOnError(RuntimeException.class, { e -> log.error(e.getMessage())})
                .block()

        log.info("Response " + clazz.getSimpleName() + ": " + objectMapper.writeValueAsString(response))
        return response
    }

    protected def postForFlux(final Payload payload, final Class clazz) {
        def response = post(payload)
                .bodyToFlux(clazz)
                .retryWhen(Retry.indefinitely())
                .doOnError(RuntimeException.class, { e -> log.error(e.getMessage())})
                .toStream()
                .collect(Collectors.toList())

        log.info("Response " + clazz.getSimpleName() + ": " + objectMapper.writeValueAsString(response))
        return response
    }

    private WebClient.ResponseSpec post(final Payload payload) {
        Thread.sleep(1000)

        log.info("Request: " + objectMapper.writeValueAsString(payload))

        try {
            final def b64payload = Base64.encoder.encodeToString(objectMapper.writeValueAsString(payload).getBytes())
            final def signature = buildSignature(b64payload)

            return webClient.post()
                    .uri(geminiProperties.baseUrl + payload.getRequest())
                    .contentLength(0)
                    .contentType(MediaType.TEXT_PLAIN)
                    .header("X-GEMINI-APIKEY", System.getenv(geminiProperties.authKey))
                    .header("X-GEMINI-PAYLOAD", b64payload)
                    .header("X-GEMINI-SIGNATURE", signature)
                    .header("Cache-Control", "no-cache")
                    .retrieve()
        }
        catch(WebClientResponseException hcee) {
            throw new RuntimeException(hcee.responseBodyAsString, hcee)
        }
        catch(Exception e) {
            throw new RuntimeException(
                    "Error creating request for POST to: " +
                    geminiProperties.baseUrl + payload.getRequest(), e
            )
        }
    }

    private String buildSignature(final String data) {
            return Hex.encode(mac.doFinal(data.getBytes())).toString()
    }
}
