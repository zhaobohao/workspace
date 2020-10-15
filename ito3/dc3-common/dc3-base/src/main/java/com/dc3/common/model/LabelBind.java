package com.dc3.common.model;

import com.dc3.common.valid.Insert;
import com.dc3.common.valid.Update;
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
