package com.protonmail.kschay.cryptotrader.job

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "job")
class JobProperties {
    String name
}
