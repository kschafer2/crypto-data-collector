package com.protonmail.kschay.cryptotrader.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.protonmail.kschay.cryptotrader.domain.currency.CurrencyList;
import com.protonmail.kschay.cryptotrader.domain.email.Email;
import com.protonmail.kschay.cryptotrader.domain.email.EmailProperties;
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiProperties;
import com.protonmail.kschay.cryptotrader.domain.symbol.SymbolList;
import com.protonmail.kschay.cryptotrader.job.JobProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties( {JobProperties.class, GeminiProperties.class, EmailProperties.class} )
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
    CurrencyList currencyList() {
        return currencyList;
    }

    @Bean
    public SymbolList symbolList() {
        SymbolList symbolList = new SymbolList();
        currencyList().forEach(c -> symbolList.addAll(c.symbols()));
        return symbolList;
    }
}
