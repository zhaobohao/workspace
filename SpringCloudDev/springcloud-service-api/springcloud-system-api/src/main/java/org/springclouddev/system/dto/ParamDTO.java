package org.springclouddev.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springclouddev.system.entity.Param;

/**
 * 数据传输对象实体类
 *
 * @author zhaobohao
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ParamDTO extends Param {
	private static final long serialVersionUID = 1L;

}
