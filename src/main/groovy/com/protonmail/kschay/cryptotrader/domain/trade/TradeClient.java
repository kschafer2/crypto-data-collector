package com.protonmail.kschay.cryptotrader.domain.trade;

import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol;

public interface TradeClient {

    TradeList getTradesSinceClose(Symbol symbol);
}
