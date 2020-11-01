package com.protonmail.kschay.cryptotrader.step.action;

import com.protonmail.kschay.cryptotrader.domain.currency.Currency;
import com.protonmail.kschay.cryptotrader.domain.currency.CurrencyList;
import com.protonmail.kschay.cryptotrader.domain.strength.CurrencyStrength;
import com.protonmail.kschay.cryptotrader.domain.strength.StrengthRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class StrengthReader implements ItemReader<CurrencyStrength> {

    private final StrengthRepository strengthRepository;
    private Iterator<Currency> currencyIterator;

    public StrengthReader(StrengthRepository strengthRepository,
                          CurrencyList currencyList) {
        this.strengthRepository = strengthRepository;
        this.currencyIterator = currencyList.iterator();
    }

    @Override
    public CurrencyStrength read() {
        return currencyIterator.hasNext() ? strengthRepository.getCurrencyStrength(currencyIterator.next()) : null;
    }
}
