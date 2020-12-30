package com.protonmail.kschay.cryptodatacollector.step.ema;

import com.protonmail.kschay.cryptodatacollector.domain.Ema;
import com.protonmail.kschay.cryptodatacollector.domain.EmaRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmaWriter implements ItemWriter<Ema> {

    private final EmaRepository emaRepository;

    public EmaWriter(EmaRepository emaRepository) {
        this.emaRepository = emaRepository;
    }

    @Override
    public void write(final List<? extends Ema> emas) {
        emas.forEach(emaRepository::insert);
    }
}
