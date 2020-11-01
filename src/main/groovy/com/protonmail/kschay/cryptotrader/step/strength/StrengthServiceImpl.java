package com.protonmail.kschay.cryptotrader.step.strength;

import com.protonmail.kschay.cryptotrader.domain.close.CloseRepository;
import com.protonmail.kschay.cryptotrader.domain.currency.Currency;
import com.protonmail.kschay.cryptotrader.domain.ema.EmaRepository;
import com.protonmail.kschay.cryptotrader.domain.strength.Strength;
import com.protonmail.kschay.cryptotrader.domain.strength.StrengthService;
import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.protonmail.kschay.cryptotrader.domain.strength.Strength.STRONG;
import static com.protonmail.kschay.cryptotrader.domain.strength.Strength.WEAK;

@Component
public class StrengthServiceImpl implements StrengthService {

    private final CloseRepository closeRepository;
    private final EmaRepository emaRepository;

    public StrengthServiceImpl(CloseRepository closeRepository,
                               EmaRepository emaRepository) {
        this.closeRepository = closeRepository;
        this.emaRepository = emaRepository;
    }

    public Strength determineStrength(Currency currency) {
        List<Symbol> symbols = currency.symbols();
        return symbols.stream().filter(this::isStrong).count() == symbols.size() ? STRONG : WEAK;
    }

    private boolean isStrong(Symbol symbol) {
        return closeRepository.getClosePrice(symbol) > emaRepository.getEmaValue(symbol);
    }
}
