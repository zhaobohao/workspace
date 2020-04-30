
package org.springclouddev.integral.dto;

import org.springclouddev.integral.entity.AccountIntegralDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账户积分交易明细表数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountIntegralDetailDTO extends AccountIntegralDetail {
	private static final long serialVersionUID = 1L;

}
