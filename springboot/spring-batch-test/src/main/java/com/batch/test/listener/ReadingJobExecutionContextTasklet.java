package com.batch.test.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * 存储JobExecutionContext中并访问
 */
@Component
@StepScope
public class ReadingJobExecutionContextTasklet implements Tasklet, StepExecutionListener {
    private String value;

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExecutionContext jobExecutionContext = stepExecution.getJobExecution().getExecutionContext();

        jobExecutionContext.put("key", value);
        //Return null to leave the old value unchanged.
        return null;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        return null;
    }
}
