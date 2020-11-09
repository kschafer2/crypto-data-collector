package com.protonmail.kschay.cryptotrader.domain.fee

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Fee {
    @JsonProperty("api_maker_fee_bps")
    Integer makerFeeBps
    @JsonProperty("api_taker_fee_bps")
    Integer takerFeeBps
    @JsonProperty("api_auction_fee_bps")
    Integer auctionFeeBps
}
