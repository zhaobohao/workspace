package org.springclouddev.integral.dto;

import org.springclouddev.integral.entity.IntegralAct;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 积分活动管理表数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IntegralActDTO extends IntegralAct {
	private static final long serialVersionUID = 1L;

}
