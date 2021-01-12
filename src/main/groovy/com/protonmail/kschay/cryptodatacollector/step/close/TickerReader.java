package com.protonmail.kschay.cryptodatacollector.step.close;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.protonmail.kschay.cryptodatacollector.domain.*;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Iterator;

@Component
public class TickerReader extends MyWebClient implements TickerClient, ItemReader<Ticker> {

    @Value("${gemini-exchange-service.url}")
    private String geminiExchangeServiceUrl;

    private Iterator<Symbol> symbolIterator;

    public TickerReader(WebClient webClient,
                        ObjectMapper objectMapper,
                        SymbolList symbolList) {
        super(webClient, objectMapper);
        this.symbolIterator = symbolList.iterator();
    }

    @Override
    public Ticker read() {
        return symbolIterator.hasNext() ? getTicker(symbolIterator.next()) : null;
    }

    @Override
    public Ticker getTicker(final Symbol symbol) {
        log.info("Getting Ticker for: " + symbol);
        return (Ticker) getForMono(geminiExchangeServiceUrl + "/ticker/" + symbol, Ticker.class);
    }
}
