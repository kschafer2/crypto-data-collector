package com.protonmail.kschay.cryptodatacollector.step.close;

import com.protonmail.kschay.cryptodatacollector.domain.Close;
import com.protonmail.kschay.cryptodatacollector.domain.Ticker;
import com.protonmail.kschay.cryptodatacollector.job.DataService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.time.LocalDateTime.now;

@Component
public class TickerProcessor implements ItemProcessor<Ticker, Close> {

    private final DataService dataService;

    public TickerProcessor(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public Close process(final Ticker ticker) {
        Optional<Close> latestClose = dataService.getLatestClose(ticker.getSymbol());

        if(latestClose.isPresent()) {
            Close close = latestClose.get();
            close.setPrice(ticker.dayClosePrice());
            close.setUpdateTime(now());
            return close;
        }
        return new Close(ticker);
    }
}
