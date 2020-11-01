package com.protonmail.kschay.cryptotrader.step.action;

import com.protonmail.kschay.cryptotrader.domain.action.Action;
import com.protonmail.kschay.cryptotrader.domain.action.CurrencyAction;
import com.protonmail.kschay.cryptotrader.domain.currency.Currency;
import com.protonmail.kschay.cryptotrader.domain.strength.CurrencyStrength;
import com.protonmail.kschay.cryptotrader.domain.strength.Strength;
import com.protonmail.kschay.cryptotrader.domain.strength.StrengthRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import static com.protonmail.kschay.cryptotrader.domain.currency.Currency.BTC;

@Component
public class StrengthProcessor implements ItemProcessor<CurrencyStrength, CurrencyAction> {

    private final StrengthRepository strengthRepository;

    public StrengthProcessor(StrengthRepository strengthRepository) {
        this.strengthRepository = strengthRepository;
    }

    @Override
    public CurrencyAction process(CurrencyStrength currencyStrength) {
        Currency currency = currencyStrength.getCurrency();
        Strength strength = currencyStrength.getStrength();
        Strength previousStrength = strengthRepository.getPreviousStrengthValue(currency);

        if(currency.equals(BTC)) {
            return new CurrencyAction(currency, Action.of(previousStrength, strength));
        }
        Strength btcStrength = strengthRepository.getStrengthValue(BTC);
        Strength previousBtcStrength = strengthRepository.getPreviousStrengthValue(BTC);

        return new CurrencyAction(currency, Action.of(previousStrength, strength, previousBtcStrength, btcStrength));
    }
}
