package com.dc3.common.model;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 标签表
 *

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Label extends Description {

    private String name;
    private String color;
}
