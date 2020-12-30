package com.protonmail.kschay.cryptodatacollector.step.ema;

import com.protonmail.kschay.cryptodatacollector.domain.Close;
import com.protonmail.kschay.cryptodatacollector.domain.CloseRepository;
import com.protonmail.kschay.cryptodatacollector.domain.Symbol;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class CloseReader implements ItemReader<Close> {

    private final CloseRepository closeRepository;
    private final Iterator<Symbol> symbolIterator;

    public CloseReader(CloseRepository closeRepository,
                       List<Symbol> symbols) {
        this.closeRepository = closeRepository;
        this.symbolIterator = symbols.iterator();
    }

    @Override
    public Close read() {
        return symbolIterator.hasNext() ? closeRepository.getClose(symbolIterator.next()) : null;
    }
}
