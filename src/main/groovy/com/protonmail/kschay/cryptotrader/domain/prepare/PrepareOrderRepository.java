package com.protonmail.kschay.cryptotrader.domain.prepare;

import java.util.List;

public interface PrepareOrderRepository {

    boolean insert(PrepareOrder prepareOrder);

    List<PrepareOrder> getPrepareOrders();
}
