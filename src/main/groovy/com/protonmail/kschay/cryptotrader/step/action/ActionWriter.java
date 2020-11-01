package com.protonmail.kschay.cryptotrader.step.action;

import com.protonmail.kschay.cryptotrader.domain.action.ActionRepository;
import com.protonmail.kschay.cryptotrader.domain.action.CurrencyAction;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActionWriter implements ItemWriter<CurrencyAction> {

    private final ActionRepository actionRepository;

    public ActionWriter(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Override
    public void write(List<? extends CurrencyAction> actions) {
        actions.forEach(actionRepository::insert);
    }
}
