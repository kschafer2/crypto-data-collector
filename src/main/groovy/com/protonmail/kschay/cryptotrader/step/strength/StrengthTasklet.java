package com.protonmail.kschay.cryptotrader.step.strength;

import com.protonmail.kschay.cryptotrader.domain.currency.CurrencyList;
import com.protonmail.kschay.cryptotrader.domain.strength.CurrencyStrength;
import com.protonmail.kschay.cryptotrader.domain.strength.Strength;
import com.protonmail.kschay.cryptotrader.domain.strength.StrengthRepository;
import com.protonmail.kschay.cryptotrader.domain.strength.StrengthService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class StrengthTasklet implements Tasklet {

    private final StrengthRepository strengthRepository;
    private final StrengthService strengthService;
    private final CurrencyList currencyList;

    public StrengthTasklet(StrengthRepository strengthRepository,
                           StrengthService strengthService,
                           CurrencyList currencyList) {
        this.strengthRepository = strengthRepository;
        this.strengthService = strengthService;
        this.currencyList = currencyList;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        currencyList.forEach(currency -> {
            Strength strength = strengthService.determineStrength(currency);
            strengthRepository.insert(new CurrencyStrength(currency, strength));
        });
        return RepeatStatus.FINISHED;
    }
}
