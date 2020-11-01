package com.protonmail.kschay.cryptotrader.step.close;

import com.protonmail.kschay.cryptotrader.domain.close.Close;
import com.protonmail.kschay.cryptotrader.domain.ticker.Ticker;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TickerProcessor implements ItemProcessor<Ticker, Close> {

    @Override
    public Close process(Ticker ticker) {
        return new Close(ticker);
    }
}
