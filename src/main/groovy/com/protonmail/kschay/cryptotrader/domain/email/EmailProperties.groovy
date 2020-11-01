package com.protonmail.kschay.cryptotrader.domain.email

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "email")
class EmailProperties {
    String to
    String from
}
