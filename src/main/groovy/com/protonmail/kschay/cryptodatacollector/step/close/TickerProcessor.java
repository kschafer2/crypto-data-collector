package com.protonmail.kschay.cryptodatacollector.step.close;

import com.protonmail.kschay.cryptodatacollector.domain.Close;
import com.protonmail.kschay.cryptodatacollector.domain.Ticker;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TickerProcessor implements ItemProcessor<Ticker, Close> {

    @Override
    public Close process(final Ticker ticker) {
        return new Close(ticker);
    }
}
