package com.protonmail.kschay.cryptodatacollector.job

import com.protonmail.kschay.cryptodatacollector.domain.Ema
import com.protonmail.kschay.cryptodatacollector.domain.Symbol
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "job")
class JobProperties {
    String name
    Map<Symbol, Ema> symbolEmas = new HashMap<>();
}
