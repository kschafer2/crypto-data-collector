package com.protonmail.kschay.cryptotrader.domain.execute

import com.protonmail.kschay.cryptotrader.domain.currency.Currency
import com.protonmail.kschay.cryptotrader.domain.fee.Fee
import com.protonmail.kschay.cryptotrader.domain.options.Options
import com.protonmail.kschay.cryptotrader.domain.order.Order
import com.protonmail.kschay.cryptotrader.domain.prepare.PrepareOrder
import com.protonmail.kschay.cryptotrader.domain.side.Side
import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol
import com.protonmail.kschay.cryptotrader.domain.type.Type

import java.math.RoundingMode

import static com.protonmail.kschay.cryptotrader.domain.side.Side.*
import static com.protonmail.kschay.cryptotrader.domain.symbol.Symbol.symbolOf
import static com.protonmail.kschay.cryptotrader.util.Converter.toBigDecimal

class ExecuteOrder {
    Symbol symbol
    String amount
    Fee fee
    String price
    Side side
    String type
    Object[] options

    ExecuteOrder(){}

    ExecuteOrder(PrepareOrder prepareOrder){
        this.symbol = prepareOrder.symbol
        this.side = prepareOrder.side
        this.type = Type.EXCHANGE_LIMIT.displayName
        this.options = List.of(Options.MAKER_OR_CANCEL.displayName).toArray()
    };

    ExecuteOrder(Order order) {
        this.symbol = symbolOf(order.symbol)
        this.amount = order.remainingAmount
        this.side = sideOf(order.side)
        this.options = order.options
    }

    void setAmount(String value) {
        if(side == null || price == null || toBigDecimal(price) <= BigDecimal.ZERO) {
            return
        }
        if(side == BUY) {
            amount = calculateAmount(deductTakerFee(value).toPlainString())
        }
        else if(side == SELL) {
            amount = value
        }
    }

    boolean amountIsBelowTradeMinimum() {
        return toBigDecimal(amount) < symbol.base().tradeMinimum
    }

    private String calculateAmount(String value) {
        return toBigDecimal(value)
                .divide(toBigDecimal(price), 8, RoundingMode.DOWN)
                .toPlainString()
    }

    private BigDecimal deductTakerFee(String value) {
        return toBigDecimal(value)
                .divide(getMultiplier(fee.takerFeeBps), scale, RoundingMode.DOWN)
    }

    private static BigDecimal getMultiplier(final Integer bpsFee) {
        def multiplier = BigDecimal.ONE.add(toDecimal(bpsFee))
        return multiplier
    }

    private static BigDecimal toDecimal(final Integer bpsFee) {
        def decimal = BigDecimal.valueOf(bpsFee)
                .divide(BigDecimal.valueOf(10000))
        return decimal
    }

    private Integer getScale() {
        if(symbol.quote() == Currency.USD && side == BUY) return 2
        return 8
    }
}
