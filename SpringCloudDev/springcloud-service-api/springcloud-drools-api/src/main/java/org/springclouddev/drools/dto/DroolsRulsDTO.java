package org.springclouddev.drools.dto;

import lombok.*;
import org.springclouddev.drools.entity.DroolsRuls;

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
public class DroolsRulsDTO extends DroolsRuls {
    private static final long serialVersionUID = 1L;

}
