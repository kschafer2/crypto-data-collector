package com.protonmail.kschay.cryptodatacollector.domain;

import java.sql.Timestamp;

public interface CloseRepository {

    boolean insert(Close close);

    boolean closeExists(Symbol symbol, Timestamp date);

    boolean update(Close close);

    Close getClose(Symbol symbol);

    Double getClosePrice(Symbol symbol);
}
