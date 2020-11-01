package com.protonmail.kschay.cryptotrader.step.close;

import com.protonmail.kschay.cryptotrader.domain.close.Close;
import com.protonmail.kschay.cryptotrader.domain.ticker.Ticker;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloseStepConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final TickerReader tickerReader;
    private final TickerProcessor tickerProcessor;
    private final CloseWriter closeWriter;

    public CloseStepConfig(StepBuilderFactory stepBuilderFactory,
                           TickerReader tickerReader,
                           TickerProcessor tickerProcessor,
                           CloseWriter closeWriter) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.tickerReader = tickerReader;
        this.tickerProcessor = tickerProcessor;
        this.closeWriter = closeWriter;
    }

    @Bean
    public Step close() {
        return stepBuilderFactory.get("close")
                .<Ticker, Close> chunk(1)
                .reader(tickerReader)
                .processor(tickerProcessor)
                .writer(closeWriter)
                .build();
    }
}
