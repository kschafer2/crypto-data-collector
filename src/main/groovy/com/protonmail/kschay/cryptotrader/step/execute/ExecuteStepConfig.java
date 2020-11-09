package com.protonmail.kschay.cryptotrader.step.execute;

import com.protonmail.kschay.cryptotrader.domain.fee.FeeClient;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecuteStepConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final ExecuteTasklet executeTasklet;

    public ExecuteStepConfig(StepBuilderFactory stepBuilderFactory,
                             ExecuteTasklet executeTasklet,
                             FeeClient feeClient) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.executeTasklet = executeTasklet;
    }

    @Bean
    public Step execute() {
        return stepBuilderFactory.get("execute")
                .tasklet(executeTasklet)
                .build();
    }
}
