
package org.springclouddev.integral.dto;

import org.springclouddev.integral.entity.IntegralAdjustAction;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 积分调整动作表数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IntegralAdjustActionDTO extends IntegralAdjustAction {
	private static final long serialVersionUID = 1L;

}
