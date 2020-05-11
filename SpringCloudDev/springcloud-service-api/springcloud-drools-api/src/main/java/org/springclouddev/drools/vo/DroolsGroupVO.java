package org.springclouddev.drools.vo;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import org.springclouddev.drools.entity.DroolsGroup;

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
@ApiModel(value = "DroolsGroupVO对象", description = "DroolsGroupVO对象")
public class DroolsGroupVO extends DroolsGroup {
    private static final long serialVersionUID = 1L;
}

