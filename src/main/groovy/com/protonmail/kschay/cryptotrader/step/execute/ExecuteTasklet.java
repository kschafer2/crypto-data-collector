package com.protonmail.kschay.cryptotrader.step.execute;

import com.protonmail.kschay.cryptotrader.domain.execute.ExecuteOrder;
import com.protonmail.kschay.cryptotrader.domain.execute.ExecuteService;
import com.protonmail.kschay.cryptotrader.domain.prepare.PrepareOrderRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ExecuteTasklet implements Tasklet {

    private final PrepareOrderRepository prepareOrderRepository;
    private final ExecuteService executeService;

    public ExecuteTasklet(PrepareOrderRepository prepareOrderRepository,
                          ExecuteService executeService) {
        this.prepareOrderRepository = prepareOrderRepository;
        this.executeService = executeService;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        prepareOrderRepository.getPrepareOrders()
                .stream()
                .map(ExecuteOrder::new)
                .forEach(executeService::placeOrder);

        return RepeatStatus.FINISHED;
    }
}
