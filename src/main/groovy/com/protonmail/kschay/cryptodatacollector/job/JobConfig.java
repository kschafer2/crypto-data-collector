package com.protonmail.kschay.cryptodatacollector.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class JobConfig {

    private final JobProperties jobProperties;
    private final JobBuilderFactory jobBuilderFactory;
    private final JobExecutionListener jobListener;
    private final Step close;
    private final Step ema;

    public JobConfig(JobProperties jobProperties,
                     JobBuilderFactory jobBuilderFactory,
                     JobExecutionListener jobListener,
                     Step close,
                     Step ema) {
        this.jobProperties = jobProperties;
        this.jobBuilderFactory = jobBuilderFactory;
        this.jobListener = jobListener;
        this.close = close;
        this.ema = ema;
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get(jobProperties.getName())
                .incrementer(new RunIdIncrementer())
                .listener(jobListener)
                .flow(close)
                .next(ema)
                .end()
                .build();
    }
}
