
package org.springclouddev.integral.dto;

import org.springclouddev.integral.entity.GrpCls;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统组群表数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GrpClsDTO extends GrpCls {
	private static final long serialVersionUID = 1L;

}
