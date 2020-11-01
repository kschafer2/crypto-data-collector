package com.protonmail.kschay.cryptotrader.domain.action;

import com.protonmail.kschay.cryptotrader.domain.currency.Currency;

public interface ActionRepository {

    boolean insert(CurrencyAction currencyAction);

    boolean exists(CurrencyAction currencyAction);

    boolean update(CurrencyAction currencyAction);

    CurrencyAction getCurrencyAction(Currency currency);
}
