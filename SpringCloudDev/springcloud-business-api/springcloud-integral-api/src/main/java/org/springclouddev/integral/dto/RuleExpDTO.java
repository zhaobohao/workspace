
package org.springclouddev.integral.dto;

import org.springclouddev.integral.entity.RuleExp;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 规则表达式表数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RuleExpDTO extends RuleExp {
	private static final long serialVersionUID = 1L;

}
