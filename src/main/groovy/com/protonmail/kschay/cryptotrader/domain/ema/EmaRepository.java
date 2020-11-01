package com.protonmail.kschay.cryptotrader.domain.ema;


import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol;

import java.sql.Timestamp;

public interface EmaRepository {

    Double getEmaValue(Symbol symbol);

    Double getPreviousEmaValue(Symbol symbol);

    boolean emaExists(Symbol symbol, Timestamp date);

    boolean insert(Ema ema);

    boolean update(Ema ema);
}
