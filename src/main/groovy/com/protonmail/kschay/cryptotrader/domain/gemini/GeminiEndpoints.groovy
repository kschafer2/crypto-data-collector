package com.protonmail.kschay.cryptotrader.domain.gemini

enum GeminiEndpoints {
    GET_TICKER("/v2/ticker/"),
    GET_SIMPLE_TICKER("/v1/pubticker/"),
    NEW_ORDER("/v1/order/new"),
    CANCEL_ORDER("/v1/order/cancel"),
    CANCEL_ALL_SESSION_ORDERS("/v1/order/cancel/session"),
    CANCEL_ALL_ACTIVE_ORDERS("/v1/order/cancel/all"),
    ORDER_STATUS("/v1/order/status"),
    GET_ACTIVE_ORDERS("/v1/orders"),
    GET_PAST_TRADES("/v1/mytrades"),
    GET_TRADE_VOLUME("/v1/tradevolume"),
    GET_NOTIONAL_VOLUME("/v1/notionalvolume"),
    HEARTBEAT("/v1/heartbeat"),
    GET_AVAILABLE_BALANCES("/v1/balances"),
    GET_NOTIONAL_BALANCES("/v1/notionalbalances"),
    TRANSFERS("/v1/transfers"),
    GET_INSTANT_QUOTE("/v1/instant/quote"),
    EXECUTE_INSTANT_ORDER("/v1/instant/execute")

    private String uri

    GeminiEndpoints(final String uri) {
        this.uri = uri
    }

    String uri() {
        return this.uri
    }
}