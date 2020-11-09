package com.protonmail.kschay.cryptotrader.step.execute;

import com.protonmail.kschay.cryptotrader.domain.balance.BalanceClient;
import com.protonmail.kschay.cryptotrader.domain.execute.ExecuteOrder;
import com.protonmail.kschay.cryptotrader.domain.execute.ExecuteService;
import com.protonmail.kschay.cryptotrader.domain.fee.Fee;
import com.protonmail.kschay.cryptotrader.domain.fee.FeeClient;
import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol;
import com.protonmail.kschay.cryptotrader.domain.ticker.SimpleTickerClient;
import com.protonmail.kschay.cryptotrader.domain.trade.Trade;
import com.protonmail.kschay.cryptotrader.domain.trade.TradeClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.protonmail.kschay.cryptotrader.domain.side.Side.BUY;
import static com.protonmail.kschay.cryptotrader.domain.side.Side.SELL;


@Service
public class ExecuteServiceImpl implements ExecuteService {

    private final SimpleTickerClient simpleTickerClient;
    private final Fee fee;
    private final BalanceClient balanceClient;
    private final TradeClient tradeClient;

    public ExecuteServiceImpl(SimpleTickerClient simpleTickerClient,
                              FeeClient feeClient,
                              BalanceClient balanceClient,
                              TradeClient tradeClient) {
        this.simpleTickerClient = simpleTickerClient;
        this.fee = feeClient.getFee();
        this.balanceClient = balanceClient;
        this.tradeClient = tradeClient;
    }

    @Override
    public ExecuteOrder prepareToExecute(ExecuteOrder executeOrder) {
        executeOrder.setFee(this.fee);
        Symbol symbol = executeOrder.getSymbol();

        if(executeOrder.getSide().equals(BUY)) {
            executeOrder.setPrice(simpleTickerClient.getSimpleTicker(symbol).getAsk());
            executeOrder.setAmount(balanceClient.getCurrentBalance(symbol.quote()).getAvailable());
        }
        else if(executeOrder.getSide().equals(SELL)) {
            executeOrder.setPrice(simpleTickerClient.getSimpleTicker(symbol).getBid());
            executeOrder.setAmount(balanceClient.getCurrentBalance(symbol.base()).getAvailable());
        }
        return executeOrder;
    }

    private ExecuteOrder subtractExecutedTrades(ExecuteOrder executeOrder) {
        tradeClient.getTradesSinceClose(executeOrder.getSymbol())
                .stream()
                .map(Trade::getAmount)
                .forEach(executeOrder::subtractFromAmount);

        if(executeOrder.getAmount().equals(BigDecimal.ZERO.toString())) {
            return null;
        }
        return executeOrder;
    }
}
