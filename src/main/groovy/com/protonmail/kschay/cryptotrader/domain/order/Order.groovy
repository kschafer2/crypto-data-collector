package com.protonmail.kschay.cryptotrader.domain.order

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Order {
    String id
    String symbol
    String exchange
    @JsonProperty("avg_execution_price")
    String avgExecutionPrice
    String side
    String type
    String timestamp
    @JsonProperty("timestampms")
    String timestampMs
    @JsonProperty("is_live")
    String isLive
    @JsonProperty("is_cancelled")
    Boolean isCancelled
    @JsonProperty("executed_amount")
    String executedAmount
    @JsonProperty("remaining_amount")
    String remainingAmount
    String[] options
    String price
    @JsonProperty("original_amount")
    String originalAmount
}


