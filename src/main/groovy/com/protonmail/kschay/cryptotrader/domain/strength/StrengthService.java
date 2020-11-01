package com.protonmail.kschay.cryptotrader.domain.strength;

import com.protonmail.kschay.cryptotrader.domain.currency.Currency;

public interface StrengthService {
    Strength determineStrength(Currency currency);
}
