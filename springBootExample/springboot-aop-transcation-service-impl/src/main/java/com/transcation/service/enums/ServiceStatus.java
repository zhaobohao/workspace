package com.transcation.service.enums;

/**
 * create by: zhaobo
 * description: service执行的状态
 * create time: 2020/6/10 16:19
 *
 */
public enum ServiceStatus {
    SUCCESS(0),FAILS(1),TIMEOUT(99),Doubt(10);

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
