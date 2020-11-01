package com.protonmail.kschay.cryptotrader.step.action;

import com.protonmail.kschay.cryptotrader.domain.action.CurrencyAction;
import com.protonmail.kschay.cryptotrader.domain.strength.CurrencyStrength;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActionStepConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final StrengthReader strengthReader;
    private final StrengthProcessor strengthProcessor;
    private final ActionWriter actionWriter;

    public ActionStepConfig(StepBuilderFactory stepBuilderFactory,
                            StrengthReader strengthReader,
                            StrengthProcessor strengthProcessor,
                            ActionWriter actionWriter) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.strengthReader = strengthReader;
        this.strengthProcessor = strengthProcessor;
        this.actionWriter = actionWriter;
    }

    @Bean
    Step action() {
        return stepBuilderFactory.get("action")
                .<CurrencyStrength, CurrencyAction>chunk(1)
                .reader(strengthReader)
                .processor(strengthProcessor)
                .writer(actionWriter)
                .build();
    }
}
