package com.protonmail.kschay.cryptotrader.domain.type

enum Type {
    EXCHANGE_LIMIT("exchange limit")

    String displayName

    private Type(String displayName) {
        this.displayName = displayName
    }
}