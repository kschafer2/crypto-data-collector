package com.protonmail.kschay.cryptotrader.domain.gemini

import com.fasterxml.jackson.databind.ObjectMapper
import com.protonmail.kschay.cryptotrader.domain.log.Logging
import com.protonmail.kschay.cryptotrader.domain.payload.Payload
import org.springframework.http.MediaType
import org.springframework.security.crypto.codec.Hex
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.security.InvalidKeyException

abstract class GeminiPrivateClient extends Logging {

    private final ObjectMapper objectMapper
    private final GeminiProperties geminiProperties
    private final WebClient webClient

    GeminiPrivateClient(ObjectMapper objectMapper,
                        GeminiProperties geminiProperties,
                        WebClient webClient) {
        this.objectMapper = objectMapper
        this.geminiProperties = geminiProperties
        this.webClient = webClient
    }

    protected WebClient.ResponseSpec post(final Payload payload) {
        Thread.sleep(1000)

        log.info("Payload: " + objectMapper.writeValueAsString(payload))

        try {
            final def b64payload = Base64.encoder.encodeToString(objectMapper.writeValueAsString(payload).getBytes())
            final def signature = buildSignature(System.getenv(geminiProperties.authSecret), b64payload)

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

    private static String buildSignature(final String secret, final String data) {
        try {
            final Mac mac = Mac.getInstance("HmacSHA384")
            final SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA384")
            mac.init(secretKeySpec)
            return Hex.encode(mac.doFinal(data.getBytes())).toString()
        }
        catch (InvalidKeyException ignored) {
            throw new RuntimeException("Invalid key exception while converting to HMac SHA256")
        }
    }
}
