package org.springclouddev.integral.dto;

import org.springclouddev.integral.entity.CustIntegralDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户积分明细表数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustIntegralDetailDTO extends CustIntegralDetail {
	private static final long serialVersionUID = 1L;

}
