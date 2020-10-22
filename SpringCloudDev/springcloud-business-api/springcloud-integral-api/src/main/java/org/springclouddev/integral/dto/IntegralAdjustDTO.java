package org.springclouddev.integral.dto;

import org.springclouddev.integral.entity.IntegralAdjust;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 积分调整表数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IntegralAdjustDTO extends IntegralAdjust {
	private static final long serialVersionUID = 1L;

}
