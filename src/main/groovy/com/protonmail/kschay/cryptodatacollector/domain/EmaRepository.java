package com.protonmail.kschay.cryptodatacollector.domain;

import java.sql.Timestamp;

public interface EmaRepository {

    Double getEmaValue(Symbol symbol);

    Double getPreviousEmaValue(Symbol symbol);

    boolean emaExists(Symbol symbol, Timestamp date);

    boolean insert(Ema ema);

    boolean update(Ema ema);
}
