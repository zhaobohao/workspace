package org.springclouddev.iot.manager.entity;

import org.springclouddev.iot.common.valid.Insert;
import org.springclouddev.iot.common.valid.Update;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 位号配置信息表
 *

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PointInfo extends Description {

    @NotNull(message = "point attribute id can't be empty", groups = {Insert.class, Update.class})
    private Long pointAttributeId;

    private String value;

    @NotNull(message = "device id can't be empty", groups = {Insert.class, Update.class})
    private Long deviceId;

    @NotNull(message = "point id can't be empty", groups = {Insert.class, Update.class})
    private Long pointId;
}
