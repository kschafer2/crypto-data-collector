package com.protonmail.kschay.cryptotrader.step.execute

import com.protonmail.kschay.cryptotrader.domain.balance.Balance
import com.protonmail.kschay.cryptotrader.domain.currency.Currency
import com.protonmail.kschay.cryptotrader.domain.execute.ExecuteOrder
import com.protonmail.kschay.cryptotrader.domain.execute.ExecuteService
import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol
import org.springframework.stereotype.Service

import static com.protonmail.kschay.cryptotrader.domain.side.Side.BUY
import static com.protonmail.kschay.cryptotrader.domain.side.Side.SELL

@Service
class ExecuteStepService implements ExecuteService {

    private final ExecuteStepClient executeStepClient

    ExecuteStepService(ExecuteStepClient executeStepClient) {
        this.executeStepClient = executeStepClient
    }

    @Override
    void placeOrder(ExecuteOrder executeOrder) {
        Symbol symbol = executeOrder.getSymbol()

        if(executeOrder.getFee() == null) {
            executeOrder.setFee(executeStepClient.getFee())
        }
        if(executeOrder.getSide() == BUY) {
            executeOrder.setPrice(executeStepClient.getSimpleTicker(symbol).getAsk())
            executeOrder.setAmount(getAvailableBalanceFor(symbol.quote()))
        }
        else if(executeOrder.getSide() == SELL) {
            executeOrder.setPrice(executeStepClient.getSimpleTicker(symbol).getBid())
            executeOrder.setAmount(getAvailableBalanceFor(symbol.base()))
        }
        else {
            return
        }
        if(executeOrder.amountIsBelowTradeMinimum()) {
            return
        }
        executeStepClient.placeOrder(executeOrder)
        placeOrder(executeOrder)
    }

    String getAvailableBalanceFor(Currency currency) {
        return executeStepClient.getBalances()
                    .stream()
                    .filter({ b -> (b.getCurrency() == currency) })
                    .findFirst()
                    .orElse(new Balance(currency))
                    .getAvailable()
    }
}
