package com.protonmail.kschay.cryptodatacollector.domain

interface TickerClient {
    Ticker getTicker(Symbol symbol)
}