package com.protonmail.kschay.cryptodatacollector.step.ema;

import com.protonmail.kschay.cryptodatacollector.domain.Close;
import com.protonmail.kschay.cryptodatacollector.domain.Ema;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class EmaStepConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final CloseReader closeReader;
    private final CloseProcessor closeProcessor;

    public EmaStepConfig(StepBuilderFactory stepBuilderFactory,
                         EntityManagerFactory entityManagerFactory,
                         CloseReader closeReader,
                         CloseProcessor closeProcessor) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.closeReader = closeReader;
        this.closeProcessor = closeProcessor;
    }

    @Bean
    public Step ema() {
        return stepBuilderFactory.get("ema")
                .<Close, Ema>chunk(10)
                .reader(closeReader)
                .processor(closeProcessor)
                .writer(emaJpaItemWriter())
                .build();
    }

    @Bean
    public JpaItemWriter<Ema> emaJpaItemWriter() {
        JpaItemWriter<Ema> emaJpaItemWriter = new JpaItemWriter<>();
        emaJpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return emaJpaItemWriter;
    }
}
