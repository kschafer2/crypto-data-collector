package com.protonmail.kschay.cryptotrader.step.close;

import com.protonmail.kschay.cryptotrader.domain.close.Close;
import com.protonmail.kschay.cryptotrader.domain.close.CloseRepository;
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
