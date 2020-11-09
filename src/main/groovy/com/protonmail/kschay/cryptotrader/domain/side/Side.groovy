package com.protonmail.kschay.cryptotrader.domain.side

enum Side {
    BUY, SELL

    static Side of(String input) {
        if(input.toString().equalsIgnoreCase("TO")) return SELL
        if(input.toString().equalsIgnoreCase("FROM")) return BUY
        return null
    }

    static Side sideOf(final String lowerCaseString) {
        return valueOf(lowerCaseString.toUpperCase())
    }
}
