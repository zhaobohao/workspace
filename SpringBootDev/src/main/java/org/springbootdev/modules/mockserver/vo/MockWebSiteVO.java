
package org.springbootdev.modules.mockserver.vo;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import org.springbootdev.modules.mockserver.entity.MockWebSite;

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
@ApiModel(value = "MockWebSiteVO对象", description = "MockWebSiteVO对象")
public class MockWebSiteVO extends MockWebSite {
    private static final long serialVersionUID = 1L;
}

