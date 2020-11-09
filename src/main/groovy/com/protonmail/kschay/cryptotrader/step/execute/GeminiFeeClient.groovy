package com.protonmail.kschay.cryptotrader.step.execute

import com.fasterxml.jackson.databind.ObjectMapper
import com.protonmail.kschay.cryptotrader.domain.fee.Fee
import com.protonmail.kschay.cryptotrader.domain.fee.FeeClient
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiPrivateClient
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiProperties
import com.protonmail.kschay.cryptotrader.domain.payload.Payload
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

import static com.protonmail.kschay.cryptotrader.domain.gemini.GeminiEndpoints.GET_NOTIONAL_VOLUME

@Component
class GeminiFeeClient extends GeminiPrivateClient implements FeeClient {

    GeminiFeeClient(ObjectMapper objectMapper, GeminiProperties geminiProperties, WebClient webClient) {
        super(objectMapper, geminiProperties, webClient)
    }

    @Override
    Fee getFee() {
        return post(new Payload(GET_NOTIONAL_VOLUME.uri()))
                .bodyToMono(Fee.class)
                .block()
    }
}
