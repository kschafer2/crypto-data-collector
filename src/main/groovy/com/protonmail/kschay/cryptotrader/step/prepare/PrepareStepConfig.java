package com.protonmail.kschay.cryptotrader.step.prepare;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrepareStepConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final PrepareTasklet prepareTasklet;

    public PrepareStepConfig(StepBuilderFactory stepBuilderFactory,
                             PrepareTasklet prepareTasklet) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.prepareTasklet = prepareTasklet;
    }

    @Bean
    Step prepare() {
        return stepBuilderFactory.get("prepare")
                .tasklet(prepareTasklet)
                .build();
    }
}
