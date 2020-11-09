package com.protonmail.kschay.cryptotrader.step.execute;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.protonmail.kschay.cryptotrader.domain.execute.ExecuteOrder;
import com.protonmail.kschay.cryptotrader.domain.execute.ExecuteService;
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiPrivateClient;
import com.protonmail.kschay.cryptotrader.domain.gemini.GeminiProperties;
import com.protonmail.kschay.cryptotrader.domain.order.Order;
import com.protonmail.kschay.cryptotrader.domain.order.OrderClient;
import com.protonmail.kschay.cryptotrader.domain.payload.PlaceOrderPayload;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Component
public class GeminiOrderClient extends GeminiPrivateClient implements OrderClient {

    private final ExecuteService executeService;

    public GeminiOrderClient(ObjectMapper objectMapper,
                             GeminiProperties geminiProperties,
                             WebClient webClient,
                             ExecuteService executeService) {
        super(objectMapper, geminiProperties, webClient);
        this.executeService = executeService;
    }

    @Override
    public void placeOrder(ExecuteOrder executeOrder) {
        Order order = post(new PlaceOrderPayload(executeOrder))
                .bodyToMono(Order.class)
                .block();

        if(order != null && (order.isCancelled()) &&
                !order.getRemainingAmount().equals(String.valueOf(BigDecimal.ZERO))) {
            executeOrder.setAmount(order.getRemainingAmount());
            placeOrder(executeService.prepareToExecute(executeOrder));
        }
    }
}
