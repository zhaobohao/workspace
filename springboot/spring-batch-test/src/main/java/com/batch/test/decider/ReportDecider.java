package com.batch.test.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * useage:
 * return new JobBuilder("petstore")
 *
 * .start(orderProcess())
 *
 * .next(reportDecider)
 *
 * .on("SEND").to(sendReportStep)
 *
 * .on("SKIP").end().build()
 *
 * .build();
 *
 */
public class ReportDecider implements JobExecutionDecider {

    @Override

    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

        if (true) {

            return new FlowExecutionStatus("SEND");

        }

        return new FlowExecutionStatus("SKIP");

    }

}
