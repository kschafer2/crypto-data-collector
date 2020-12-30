package com.protonmail.kschay.cryptodatacollector.step.close;

import com.protonmail.kschay.cryptodatacollector.domain.Close;
import com.protonmail.kschay.cryptodatacollector.domain.CloseRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CloseWriter implements ItemWriter<Close> {

    private final CloseRepository closeRepository;

    public CloseWriter(CloseRepository closeRepository) {
        this.closeRepository = closeRepository;
    }

    @Override
    public void write(final List<? extends Close> closes) {
        closes.forEach(closeRepository::insert);
    }
}
