package com.protonmail.kschay.cryptotrader.step.prepare;

import com.protonmail.kschay.cryptotrader.domain.action.ActionRepository;
import com.protonmail.kschay.cryptotrader.domain.action.CurrencyAction;
import com.protonmail.kschay.cryptotrader.domain.currency.CurrencyList;
import com.protonmail.kschay.cryptotrader.domain.prepare.PrepareOrder;
import com.protonmail.kschay.cryptotrader.domain.prepare.PrepareOrderRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class PrepareTasklet implements Tasklet {

    private final ActionRepository actionRepository;
    private final PrepareOrderRepository prepareOrderRepository;
    private final CurrencyList currencies;

    public PrepareTasklet(ActionRepository actionRepository,
                          PrepareOrderRepository prepareOrderRepository,
                          CurrencyList currencies) {
        this.actionRepository = actionRepository;
        this.prepareOrderRepository = prepareOrderRepository;
        this.currencies = currencies;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        currencies.stream()
                .map(actionRepository::getCurrencyAction)
                .filter(CurrencyAction::isActionable)
                .map(PrepareOrder::new)
                .forEach(prepareOrderRepository::insert);

        return RepeatStatus.FINISHED;
    }
}
