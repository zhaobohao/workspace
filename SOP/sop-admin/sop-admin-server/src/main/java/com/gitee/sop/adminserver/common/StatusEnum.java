package com.gitee.sop.adminserver.common;

import lombok.Getter;

/**
 * 通用状态枚举
 *
 * @author tanghc
 */
@Getter
public enum StatusEnum {
    /**
     * 启用
     */
    STATUS_ENABLE((byte)1),
    /**
     * 禁用
     */
    STATUS_DISABLE((byte)0),
   ;
   private byte status;

    StatusEnum(byte status) {
        this.status = status;
    }
}
