package org.springbootdev.modules.drools.dto;

import lombok.*;
import org.springbootdev.modules.drools.entity.DroolsGroup;

/**
 * 数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2020-05-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@ToString
public class DroolsGroupDTO extends DroolsGroup {
    private static final long serialVersionUID = 1L;

}
