package com.protonmail.kschay.cryptodatacollector.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.protonmail.kschay.cryptodatacollector.domain.CurrencyList;
import com.protonmail.kschay.cryptodatacollector.domain.Email;
import com.protonmail.kschay.cryptodatacollector.domain.EmailProperties;
import com.protonmail.kschay.cryptodatacollector.domain.SymbolList;
import com.protonmail.kschay.cryptodatacollector.job.JobProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties( {JobProperties.class, EmailProperties.class} )
public class AppConfig {

    private final EmailProperties emailProperties;

    @Value("#{'${currencies}'.split(',')}")
    private CurrencyList currencyList;

    public AppConfig(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Email email() {
        return new Email(emailProperties.getTo(), emailProperties.getFrom());
    }

    @Bean
    public CurrencyList currencyList() {
        return currencyList;
    }

    @Bean
    public SymbolList symbolList() {
        SymbolList symbolList = new SymbolList();
        currencyList().forEach(c -> symbolList.addAll(c.getSymbols()));
        return symbolList;
    }
}
