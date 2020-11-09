package com.protonmail.kschay.cryptotrader.step.execute;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiPrivateClient;
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiProperties;
import com.protonmail.kschay.cryptotrader.domain.payload.PastTradesPayload;
import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol;
import com.protonmail.kschay.cryptotrader.domain.trade.Trade;
import com.protonmail.kschay.cryptotrader.domain.trade.TradeClient;
import com.protonmail.kschay.cryptotrader.domain.trade.TradeList;
import com.protonmail.kschay.cryptotrader.util.DateAndTime;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GeminiTradeClient extends GeminiPrivateClient implements TradeClient {

    public GeminiTradeClient(ObjectMapper objectMapper, GeminiProperties geminiProperties, WebClient webClient) {
        super(objectMapper, geminiProperties, webClient);
    }

    @Override
    public TradeList getTradesSinceClose(Symbol symbol) {
        List<Trade> trades = post(new PastTradesPayload(symbol, DateAndTime.localCloseTimeSeconds()))
                .bodyToFlux(Trade.class)
                .toStream().collect(Collectors.toList());
        return new TradeList(trades);
    }
}