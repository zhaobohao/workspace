package com.gitee.sop.servercommon.message;

import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class ServiceErrorImpl implements ServiceError {
    private String sub_code;
    private String sub_msg;

    public ServiceErrorImpl() {
    }

    public ServiceErrorImpl(String sub_code, String sub_msg) {
        this.sub_code = sub_code;
        this.sub_msg = sub_msg;
    }
}