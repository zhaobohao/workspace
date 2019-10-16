package com.gitee.sop.gatewaycommon.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * @author tanghc
 */
@Getter
@Setter
@ToString
public class ErrorEntity {
    private String id;
    private String name;
    private String version;
    private String serviceId;
    private String errorMsg;
    private long count;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorEntity that = (ErrorEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}