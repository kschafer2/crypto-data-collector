package com.protonmail.kschay.cryptotrader.step.ema;

import com.protonmail.kschay.cryptotrader.domain.close.Close;
import com.protonmail.kschay.cryptotrader.domain.ema.Ema;
import com.protonmail.kschay.cryptotrader.domain.ema.EmaRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CloseProcessor implements ItemProcessor<Close, Ema> {

    private final EmaRepository emaRepository;

    public CloseProcessor(EmaRepository emaRepository) {
        this.emaRepository = emaRepository;
    }

    @Override
    public Ema process(final Close close) {
        return new Ema(close, emaRepository.getPreviousEmaValue(close.getSymbol()));
    }
}
