package com.dc3.common.model;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * Ip 黑名单表
 *
 * @author pnoker
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BlackIp extends Description {

    private String ip;
    private Boolean enable;
}
