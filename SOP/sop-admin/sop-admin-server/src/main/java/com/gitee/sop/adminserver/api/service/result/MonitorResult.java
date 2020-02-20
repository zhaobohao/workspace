package com.gitee.sop.adminserver.api.service.result;

import lombok.Data;

import java.util.List;

/**
 * @author tanghc
 */
@Data
public class MonitorResult {
    private List<MonitorInfoVO> monitorInfoData;
}
