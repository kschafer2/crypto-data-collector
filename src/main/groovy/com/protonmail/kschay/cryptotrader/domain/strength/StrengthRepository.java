package com.protonmail.kschay.cryptotrader.domain.strength;

import com.protonmail.kschay.cryptotrader.domain.currency.Currency;

public interface StrengthRepository {
    boolean insert(CurrencyStrength currencyStrength);

    boolean exists(CurrencyStrength currencyStrength);

    boolean update(CurrencyStrength currencyStrength);

    CurrencyStrength getCurrencyStrength(Currency currency);

    Strength getStrengthValue(Currency currency);

    Strength getPreviousStrengthValue(Currency currency);
}
