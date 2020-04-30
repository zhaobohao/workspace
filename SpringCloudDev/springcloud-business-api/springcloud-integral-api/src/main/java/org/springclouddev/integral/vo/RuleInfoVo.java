package org.springclouddev.integral.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RuleInfoVo {
    private Long ruleId;

    private String ruleName;

    private String status;

    private String statusName;

    private String ruleExp;

    private String remark;

    private String crtUser;

    private Date crtDate;

    private String lstUpdUser;

    private Date lstUpdTime;

}
