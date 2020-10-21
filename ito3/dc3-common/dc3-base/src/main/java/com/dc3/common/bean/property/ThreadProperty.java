package com.dc3.common.bean.property;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ThreadProperty {
    private String prefix;
    private int corePoolSize;
    private int maximumPoolSize;
    private int keepAliveTime;
}
