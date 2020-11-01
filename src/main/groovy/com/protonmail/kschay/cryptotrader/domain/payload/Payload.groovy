package com.protonmail.kschay.cryptotrader.domain.payload

class Payload {
    String request
    String nonce = System.currentTimeMillis().toString()

    Payload(String request) {
        this.request = request
    }
}
