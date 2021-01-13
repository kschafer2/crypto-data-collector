package com.protonmail.kschay.cryptodatacollector.step.ema;

import com.protonmail.kschay.cryptodatacollector.domain.Close;
import com.protonmail.kschay.cryptodatacollector.domain.Ema;
import com.protonmail.kschay.cryptodatacollector.job.DataService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.time.LocalDateTime.now;

@Component
public class CloseProcessor implements ItemProcessor<Close, Ema> {

    private final DataService dataService;

    public CloseProcessor(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public Ema process(final Close close) {
        Ema ema = new Ema(close, dataService.getPreviousEma(close.getSymbol()).getValue());
        Optional<Ema> latestEma = dataService.getLatestEma(close.getSymbol());

        if(latestEma.isPresent()) {
            ema.setId(latestEma.get().getId());
            ema.setUpdateTime(now());
        }
        return ema;
    }
}
