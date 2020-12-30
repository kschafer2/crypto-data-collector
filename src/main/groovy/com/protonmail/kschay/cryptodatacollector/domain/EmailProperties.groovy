package com.protonmail.kschay.cryptodatacollector.domain

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "email")
class EmailProperties {
    String to
    String from
}
