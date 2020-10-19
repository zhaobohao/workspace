package org.springclouddev.iot.manager.entity;

import org.springclouddev.iot.common.valid.Insert;
import org.springclouddev.iot.common.valid.Update;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 标签关系表
 *

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LabelBind extends Description {

    @NotNull(message = "label id can't be empty", groups = {Insert.class, Update.class})
    private Long labelId;

    @NotNull(message = "entity id can't be empty", groups = {Insert.class, Update.class})
    private Long entityId;

    private String type;
}
