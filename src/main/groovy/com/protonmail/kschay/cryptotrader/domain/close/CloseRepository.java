package com.protonmail.kschay.cryptotrader.domain.close;

import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol;

import java.sql.Timestamp;

public interface CloseRepository {

    boolean insert(Close close);

    boolean closeExists(Symbol symbol, Timestamp date);

    boolean update(Close close);

    Close getClose(Symbol symbol);

    Double getClosePrice(Symbol symbol);
}
