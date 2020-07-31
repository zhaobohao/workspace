

package com.dc3.common.model;

import com.dc3.common.valid.Insert;
import com.dc3.common.valid.Update;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 驱动配置信息表
 *
 * @author pnoker
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DriverInfo extends Description {

    @NotNull(message = "driver attribute id can't be empty", groups = {Insert.class, Update.class})
    private Long driverAttributeId;

    private String value;

    @NotNull(message = "profile id can't be empty", groups = {Insert.class, Update.class})
    private Long profileId;

}
