package com.protonmail.kschay.cryptodatacollector.step.ema;

import com.protonmail.kschay.cryptodatacollector.domain.Close;
import com.protonmail.kschay.cryptodatacollector.domain.Ema;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmaStepConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final CloseReader closeReader;
    private final CloseProcessor closeProcessor;
    private final EmaWriter emaWriter;

    public EmaStepConfig(StepBuilderFactory stepBuilderFactory,
                         CloseReader closeReader,
                         CloseProcessor closeProcessor,
                         EmaWriter emaWriter) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.closeReader = closeReader;
        this.closeProcessor = closeProcessor;
        this.emaWriter = emaWriter;
    }

    @Bean
    public Step ema() {
        return stepBuilderFactory.get("ema")
                .<Close, Ema>chunk(1)
                .reader(closeReader)
                .processor(closeProcessor)
                .writer(emaWriter)
                .build();
    }
}
