package org.springclouddev.iot.common.sdk.bean;

import lombok.Getter;
import lombok.Setter;
import org.springclouddev.iot.common.valid.Insert;
import org.springclouddev.iot.common.valid.Update;
import org.springclouddev.iot.manager.entity.DriverAttribute;
import org.springclouddev.iot.manager.entity.PointAttribute;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 驱动配置文件 driver 字段内容
 *

 */
@Setter
@Getter
@Validated({Insert.class, Update.class})
@ConfigurationProperties(prefix = "driver")
public class DriverProperty {
    @NotBlank(message = "name can't be empty")
    @Pattern(
            regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5][A-Za-z0-9\\u4e00-\\u9fa5-_]{1,31}$",
            message = "invalid name,contains invalid characters or length is not in the range of 2~32",
            groups = {Insert.class, Update.class})
    private String name;
    private String description;
    private ScheduleProperty schedule;
    private List<DriverAttribute> driverAttribute;
    private List<PointAttribute> pointAttribute;
}
