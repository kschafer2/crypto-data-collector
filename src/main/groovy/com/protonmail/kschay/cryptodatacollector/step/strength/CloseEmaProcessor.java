package com.protonmail.kschay.cryptodatacollector.step.strength;

import com.protonmail.kschay.cryptodatacollector.domain.CloseEma;
import com.protonmail.kschay.cryptodatacollector.domain.CurrencyStrength;
import com.protonmail.kschay.cryptodatacollector.domain.Strength;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.protonmail.kschay.cryptodatacollector.domain.Strength.STRONG;
import static com.protonmail.kschay.cryptodatacollector.domain.Strength.WEAK;

@Component
public class CloseEmaProcessor implements ItemProcessor<List<CloseEma>, CurrencyStrength> {

    @Override
    public CurrencyStrength process(final List<CloseEma> closeEmaList) {
        CurrencyStrength currencyStrength = new CurrencyStrength();
        currencyStrength.setCurrency(closeEmaList.get(0).getClose().getSymbol().base());

        boolean currencyIsStrong = closeEmaList.stream()
                .map(CloseEma::determineStrength)
                .allMatch(Strength::isStrong);

        currencyStrength.setValue(currencyIsStrong ? STRONG : WEAK);
        return currencyStrength;
    }
}
