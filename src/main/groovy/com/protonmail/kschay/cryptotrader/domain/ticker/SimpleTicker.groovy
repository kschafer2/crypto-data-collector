package com.protonmail.kschay.cryptotrader.domain.ticker

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class SimpleTicker {
    String ask
    String bid
}
