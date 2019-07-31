package com.gitee.easyopen.limit;

import com.gitee.easyopen.bean.ApiSearch;

/**
 * @author tanghc
 */
public class LimitSearch extends ApiSearch {
    private static final long serialVersionUID = 1L;

    private Byte status;
    private String limitType;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }

}
