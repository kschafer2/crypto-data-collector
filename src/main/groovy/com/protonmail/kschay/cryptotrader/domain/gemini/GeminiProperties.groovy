package com.protonmail.kschay.cryptotrader.domain.gemini

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "gemini")
class GeminiProperties {
    String baseUrl
    String authKey
    String authSecret
}
