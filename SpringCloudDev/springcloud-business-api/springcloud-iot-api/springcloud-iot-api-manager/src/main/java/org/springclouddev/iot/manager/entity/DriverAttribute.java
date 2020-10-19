package org.springclouddev.iot.manager.entity;

import org.springclouddev.iot.common.valid.Insert;
import org.springclouddev.iot.common.valid.Update;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 驱动配置属性表
 *

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DriverAttribute extends Description {

    @NotBlank(message = "display name can't be empty", groups = {Insert.class})
    @Pattern(regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5][A-Za-z0-9\\u4e00-\\u9fa5-_]{0,31}$", message = "invalid display name,contains invalid characters or length is not in the range of 1~32", groups = {Insert.class, Update.class})
    private String displayName;

    @NotBlank(message = "name can't be empty", groups = {Insert.class})
    @Pattern(regexp = "^[A-Za-z0-9][A-Za-z0-9-_]{1,31}$", message = "invalid name,contains invalid characters or length is not in the range of 2~32", groups = {Insert.class, Update.class})
    private String name;

    /**
     * string/int/double/float/long/boolean
     */
    private String type;
    private String value;

    @NotNull(message = "driver id can't be empty", groups = {Insert.class, Update.class})
    private Long driverId;
}
