package org.springclouddev.mockserver.vo;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import org.springclouddev.mockserver.entity.MockHttp;

/**
 * 视图实体类
 *
 * @author zhaobohao
 * @since 2020-04-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@ToString
@ApiModel(value = "MockHttpVO对象", description = "MockHttpVO对象")
public class MockHttpVO extends MockHttp {
    private static final long serialVersionUID = 1L;
}

