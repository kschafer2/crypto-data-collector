package com.protonmail.kschay.cryptodatacollector.step.close;

import com.protonmail.kschay.cryptodatacollector.domain.Symbol;
import com.protonmail.kschay.cryptodatacollector.domain.SymbolList;
import com.protonmail.kschay.cryptodatacollector.domain.Ticker;
import com.protonmail.kschay.cryptodatacollector.domain.TickerClient;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class TickerReader implements ItemReader<Ticker> {

    private final TickerClient tickerClient;
    private Iterator<Symbol> symbolIterator;

    public TickerReader(TickerClient tickerClient,
                        SymbolList symbolList) {
        this.tickerClient = tickerClient;
        this.symbolIterator = symbolList.iterator();
    }

    @Override
    public Ticker read() {
        return symbolIterator.hasNext() ? tickerClient.getTicker(symbolIterator.next()) : null;
    }
}
