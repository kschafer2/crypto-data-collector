package com.protonmail.kschay.cryptotrader.step.execute;

import com.protonmail.kschay.cryptotrader.domain.execute.ExecuteOrder;
import com.protonmail.kschay.cryptotrader.domain.prepare.PrepareOrderRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ExecuteTasklet implements Tasklet {

    private final PrepareOrderRepository prepareOrderRepository;
    private final ExecuteStepClient executeStepClient;

    public ExecuteTasklet(PrepareOrderRepository prepareOrderRepository,
                          ExecuteStepClient executeStepClient) {
        this.prepareOrderRepository = prepareOrderRepository;
        this.executeStepClient = executeStepClient;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        prepareOrderRepository.getPrepareOrders().stream()
                .map(ExecuteOrder::new)
                .forEach(executeStepClient::placeOrder);

        return RepeatStatus.FINISHED;
    }
}
