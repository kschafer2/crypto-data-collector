package com.protonmail.kschay.cryptodatacollector.step.ema;

import com.protonmail.kschay.cryptodatacollector.domain.Close;
import com.protonmail.kschay.cryptodatacollector.domain.Ema;
import com.protonmail.kschay.cryptodatacollector.domain.EmaRepository;
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
