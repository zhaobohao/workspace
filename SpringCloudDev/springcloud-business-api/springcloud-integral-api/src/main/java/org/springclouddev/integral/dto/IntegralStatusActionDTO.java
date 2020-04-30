
package org.springclouddev.integral.dto;

import org.springclouddev.integral.entity.IntegralStatusAction;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 积分冻结解冻状态动作表数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IntegralStatusActionDTO extends IntegralStatusAction {
	private static final long serialVersionUID = 1L;

}
