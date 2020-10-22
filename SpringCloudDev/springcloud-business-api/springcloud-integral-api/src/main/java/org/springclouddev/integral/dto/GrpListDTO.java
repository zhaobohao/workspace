package org.springclouddev.integral.dto;

import org.springclouddev.integral.entity.GrpList;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统组群集合表数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GrpListDTO extends GrpList {
	private static final long serialVersionUID = 1L;

}
