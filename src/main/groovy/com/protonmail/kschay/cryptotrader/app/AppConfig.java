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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableConfigurationProperties( {JobProperties.class, GeminiProperties.class, EmailProperties.class} )
public class AppConfig {

    private final EmailProperties emailProperties;
    private final GeminiProperties geminiProperties;

    @Value("#{'${currencies}'.split(',')}")
    private CurrencyList currencyList;

    public AppConfig(EmailProperties emailProperties,
                     GeminiProperties geminiProperties) {
        this.emailProperties = emailProperties;
        this.geminiProperties = geminiProperties;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public Mac mac() {
        try {
            final Mac mac = Mac.getInstance("HmacSHA384");
            final String secret = System.getenv(geminiProperties.getAuthSecret());
            final SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA384");
            mac.init(secretKeySpec);
            return mac;
        }
        catch (Exception e) {
            throw new RuntimeException("Failure while converting to HMac SHA256",e);
        }
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
