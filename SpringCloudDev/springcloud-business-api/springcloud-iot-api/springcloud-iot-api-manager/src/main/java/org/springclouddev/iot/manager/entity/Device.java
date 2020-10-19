package org.springclouddev.iot.manager.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springclouddev.iot.common.valid.Insert;
import org.springclouddev.iot.common.valid.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 设备表
 *

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Device extends Description {

    @NotBlank(message = "name can't be empty", groups = {Insert.class})
    @Pattern(regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5][A-Za-z0-9\\u4e00-\\u9fa5-_]{1,31}$", message = "invalid name,contains invalid characters or length is not in the range of 2~32", groups = {Insert.class, Update.class})
    private String name;

    /**
     * 是否结构化存储数据
     * 默认：false，为单点存储
     */
    private Boolean multi;

    @NotNull(message = "profile id can't be empty", groups = {Insert.class, Update.class})
    private Long profileId;

    @NotNull(message = "group id can't be empty", groups = {Insert.class, Update.class})
    private Long groupId;

    public Device(String name, Long profileId, Long groupId) {
        this.name = name;
        this.profileId = profileId;
        this.groupId = groupId;
    }
}
