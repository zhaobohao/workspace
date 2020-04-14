package org.springclouddev.mockserver.dto;

import lombok.*;
import org.springclouddev.mockserver.entity.MockWebSite;

/**
 * 数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2020-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@ToString
public class MockWebSiteDTO extends MockWebSite {
    private static final long serialVersionUID = 1L;

}
