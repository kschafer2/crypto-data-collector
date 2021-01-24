package com.protonmail.kschay.cryptodatacollector.step.strength;

import com.protonmail.kschay.cryptodatacollector.domain.CloseEma;
import com.protonmail.kschay.cryptodatacollector.domain.Currency;
import com.protonmail.kschay.cryptodatacollector.job.DataService;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CloseEmaReader implements ItemReader<List<CloseEma>> {

    private final DataService dataService;
    private final Iterator<Currency> currencyIterator;

    public CloseEmaReader(DataService dataService,
                          List<Currency> currencies) {
        this.dataService = dataService;
        this.currencyIterator = currencies.iterator();
    }

    @Override
    public List<CloseEma> read() {
        return currencyIterator.hasNext() ?
                currencyIterator.next()
                        .getSymbols()
                        .stream()
                        .map(dataService::getCloseEma)
                        .collect(Collectors.toList()) : null;
    }
}
