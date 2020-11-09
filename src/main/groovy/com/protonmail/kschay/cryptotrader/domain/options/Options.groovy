package com.protonmail.kschay.cryptotrader.domain.options

enum Options {
    MAKER_OR_CANCEL("maker-or-cancel"),
    IMMEDIATE_OR_CANCEL("immediate-or-cancel")

    String displayName

    private Options(String displayName) {
        this.displayName = displayName
    }

    @Override
    String toString() {
        return displayName
    }
}