package com.transcation.service;

import com.transcation.service.base.BaseServiceContext;
import com.transcation.service.utils.JacksonUtil;
import lombok.Data;

/**
 * 承接所有参数的上下文
 */
@Data
public class DeServiceServiceContext implements BaseServiceContext {
    private User user;
    @Override
    public String getData() {
        return JacksonUtil.bean2Json(this.user);
    }

    @Override
    public int retry() {
        return 3;
    }
}
