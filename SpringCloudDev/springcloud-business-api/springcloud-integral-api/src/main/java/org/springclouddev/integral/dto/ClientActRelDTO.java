package org.springclouddev.integral.dto;

import org.springclouddev.integral.entity.ClientActRel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户活动映射关系表数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientActRelDTO extends ClientActRel {
	private static final long serialVersionUID = 1L;

}
