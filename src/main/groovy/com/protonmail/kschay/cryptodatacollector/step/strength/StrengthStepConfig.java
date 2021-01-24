package com.protonmail.kschay.cryptodatacollector.step.strength;

import com.protonmail.kschay.cryptodatacollector.domain.CloseEma;
import com.protonmail.kschay.cryptodatacollector.domain.CurrencyStrength;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Configuration
public class StrengthStepConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final CloseEmaReader closeEmaReader;
    private final CloseEmaProcessor closeEmaProcessor;

    public StrengthStepConfig(StepBuilderFactory stepBuilderFactory,
                              EntityManagerFactory entityManagerFactory,
                              CloseEmaReader closeEmaReader,
                              CloseEmaProcessor closeEmaProcessor) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.closeEmaReader = closeEmaReader;
        this.closeEmaProcessor = closeEmaProcessor;
    }

    @Bean
    Step strength() {
        return stepBuilderFactory.get("strength")
                .<List<CloseEma>, CurrencyStrength>chunk(10)
                .reader(closeEmaReader)
                .processor(closeEmaProcessor)
                .writer(strengthJpaItemWriter())
                .build();
    }

    private ItemWriter<CurrencyStrength> strengthJpaItemWriter() {
        JpaItemWriter<CurrencyStrength> emaJpaItemWriter = new JpaItemWriter<>();
        emaJpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return emaJpaItemWriter;
    }
}

