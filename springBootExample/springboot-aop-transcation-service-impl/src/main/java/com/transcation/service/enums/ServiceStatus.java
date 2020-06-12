package com.transcation.service.enums;

/**
 * create by: zhaobo
 * description: service执行的状态
 * create time: 2020/6/10 16:19
 *
 */
public enum ServiceStatus {
    SUCCESS(0),
    FAILS(1),
    TIMEOUT(99),
    //供查证方法使用，如果查证方法返回DOUBT,查交易流程开始冲正。
    DOUBT(10),
    DEFAULT(20);

    ServiceStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    /**
     * 状态值
     */
    int status ;
}
