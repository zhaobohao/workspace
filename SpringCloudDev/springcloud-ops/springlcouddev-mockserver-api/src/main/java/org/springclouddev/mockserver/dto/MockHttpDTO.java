package org.springclouddev.mockserver.dto;

import lombok.*;
import org.springclouddev.mockserver.entity.MockHttp;

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
public class MockHttpDTO extends MockHttp {
    private static final long serialVersionUID = 1L;

}
