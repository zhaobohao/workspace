package org.springclouddev.iot.common.sdk.bean;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ScheduleConfig {
    private Boolean enable = false;
    private String corn = "* */1 * * * ?";
}
