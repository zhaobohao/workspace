package org.springexample.statemachine.workflow.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("state_machine_log")
public class StateMachineLog {
    private Integer id;
    private String machineCode;
    private String source;
    private String target;
    private String event;
    private String transitionResult;
    private Date createTime;
    private Date updateTime;
    private String machineContext;
    private String request;
    private String response;
}