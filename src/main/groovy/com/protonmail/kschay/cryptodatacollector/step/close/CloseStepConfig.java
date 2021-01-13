package com.protonmail.kschay.cryptodatacollector.step.close;

import com.protonmail.kschay.cryptodatacollector.domain.Close;
import com.protonmail.kschay.cryptodatacollector.domain.Ticker;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class CloseStepConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final TickerReader tickerReader;
    private final TickerProcessor tickerProcessor;

    public CloseStepConfig(StepBuilderFactory stepBuilderFactory,
                           EntityManagerFactory entityManagerFactory,
                           TickerReader tickerReader,
                           TickerProcessor tickerProcessor) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.tickerReader = tickerReader;
        this.tickerProcessor = tickerProcessor;
    }

    @Bean
    public Step close() {
        return stepBuilderFactory.get("close")
                .<Ticker, Close> chunk(10)
                .reader(tickerReader)
                .processor(tickerProcessor)
                .writer(closeJpaItemWriter())
                .build();
    }

    @Bean
    public JpaItemWriter<Close> closeJpaItemWriter() {
        JpaItemWriter<Close> closeJpaItemWriter = new JpaItemWriter<>();
        closeJpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return closeJpaItemWriter;
    }
}
