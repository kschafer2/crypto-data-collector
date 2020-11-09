package com.protonmail.kschay.cryptotrader.domain.trade;

import com.protonmail.kschay.cryptotrader.domain.currency.Currency;
import com.protonmail.kschay.cryptotrader.domain.side.Side;
import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol;
import com.protonmail.kschay.cryptotrader.util.Converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.protonmail.kschay.cryptotrader.util.Converter.toBigDecimal;

public class TradeList extends ArrayList<Trade> {

    public TradeList(Collection<? extends Trade> collection) {
        super(collection);
    }

    public Side getSide() {
        if(!this.isEmpty()) {
            return this.get(0).getSide();
        }
        return null;
    }

    public Symbol getSymbol() {
        if(!this.isEmpty()) {
            return this.get(0).getSymbol();
        }
        return null;
    }

    public Currency getStoreCurrency() {
        Side side = getSide();
        Symbol symbol = getSymbol();

        if(symbol == null) {
            return null;
        }

        if(side == Side.BUY) {
            return symbol.quote();
        }
        else if(getSide() == Side.SELL) {
            return symbol.base();
        }
        return null;
    }

    public String getTotalAmount() {
        BigDecimal totalAmount = BigDecimal.ZERO;

        List<BigDecimal> amounts = this.stream()
                .map(Trade::getAmount)
                .map(Converter::toBigDecimal)
                .collect(Collectors.toList());

        for(BigDecimal amount : amounts) {
            totalAmount = totalAmount.add(amount);
        }
        return String.valueOf(totalAmount);
    }

    public String getAveragePrice() {
        return String.valueOf(getTotalPriceAmount().divide(getTotalWeight(), 8, RoundingMode.DOWN));
    }

    public BigDecimal convertAmount() {
        return toBigDecimal(getAveragePrice()).divide(toBigDecimal(getTotalAmount()), 8, RoundingMode.DOWN);
    }

    public String subtractFromAmount(List<BigDecimal> amounts) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(BigDecimal amount : amounts) {
            totalAmount = totalAmount.add(amount);
        }
        return String.valueOf(toBigDecimal(getTotalAmount()).subtract(totalAmount));
    }

    private BigDecimal getTotalPriceAmount() {
        BigDecimal totalPriceAmount = BigDecimal.ZERO;

        List<BigDecimal> priceAmounts = this.stream()
                .map(this::getPriceAmount)
                .collect(Collectors.toList());

        for (BigDecimal priceAmount : priceAmounts) {
            totalPriceAmount = totalPriceAmount.add(priceAmount);
        }
        return totalPriceAmount;
    }

    private BigDecimal getTotalWeight() {
        BigDecimal totalWeight = BigDecimal.ZERO;

        List<BigDecimal> weights = this.stream()
                .map(Trade::getAmount)
                .map(Converter::toBigDecimal)
                .collect(Collectors.toList());

        for(BigDecimal weight : weights) {
            totalWeight = totalWeight.add(weight);
        }
        return totalWeight;
    }

    private BigDecimal getPriceAmount(Trade trade) {
        return toBigDecimal(trade.getAmount()).multiply(toBigDecimal(trade.getPrice()));
    }
}
