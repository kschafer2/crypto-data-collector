package com.protonmail.kschay.cryptotrader.step.strength;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StrengthStepConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final StrengthTasklet strengthTasklet;

    public StrengthStepConfig(StepBuilderFactory stepBuilderFactory,
                              StrengthTasklet strengthTasklet) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.strengthTasklet = strengthTasklet;
    }

    @Bean
    Step strength() {
        return stepBuilderFactory.get("strength")
                .tasklet(strengthTasklet)
                .build();
    }
}

