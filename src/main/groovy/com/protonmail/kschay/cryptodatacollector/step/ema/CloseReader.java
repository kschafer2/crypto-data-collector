package com.protonmail.kschay.cryptodatacollector.step.ema;

import com.protonmail.kschay.cryptodatacollector.domain.Close;
import com.protonmail.kschay.cryptodatacollector.domain.Symbol;
import com.protonmail.kschay.cryptodatacollector.job.DataService;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class CloseReader implements ItemReader<Close> {

    private final DataService dataService;
    private final Iterator<Symbol> symbolIterator;

    public CloseReader(DataService dataService,
                       List<Symbol> symbols) {
        this.dataService = dataService;
        this.symbolIterator = symbols.iterator();
    }

    @Override
    public Close read() {
        return symbolIterator.hasNext() ? dataService.getLatestClose(symbolIterator.next()).orElse(null) : null;
    }
}
