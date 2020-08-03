package com.dc3.common.model;

import com.dc3.common.valid.Insert;
import com.dc3.common.valid.Update;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 模板配置信息表
 *
 * @author pnoker
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PointAttribute extends Description {

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
