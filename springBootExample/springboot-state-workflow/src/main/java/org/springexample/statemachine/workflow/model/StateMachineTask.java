package org.springexample.statemachine.workflow.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("state_machine_task")
public class StateMachineTask {
    private Integer id;
    private String machineCode;
    private String machineState;
    private String machineType;
    private String scanStatus;
    private String transactionId;
    private Integer currentTrytimes;
    private Integer retryTimes;
    private Date nextRunTime;
    private Date createTime;
    private Date updateTime;
    private String machineContext;
    private String requestData;
    private String responseData;

    public boolean isLastRetry() {
        return getCurrentTrytimes() >= getRetryTimes();
    }
}