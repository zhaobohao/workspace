package org.springbootdev.modules.drools.vo;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import org.springbootdev.modules.drools.entity.DroolsRuls;

/**
 * 视图实体类
 *
 * @author zhaobohao
 * @since 2020-05-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@ToString
@ApiModel(value = "DroolsRulsVO对象", description = "DroolsRulsVO对象")
public class DroolsRulsVO extends DroolsRuls {
    private static final long serialVersionUID = 1L;
}

