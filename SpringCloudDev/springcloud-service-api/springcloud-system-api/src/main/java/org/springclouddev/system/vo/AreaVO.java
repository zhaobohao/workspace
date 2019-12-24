
package org.springclouddev.system.vo;

import org.springclouddev.system.entity.Area;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;

/**
 * 行政区划视图实体类
 *
 * @author zhaobohao
 * @since 2019-12-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AreaVO对象", description = "行政区划")
public class AreaVO extends Area {
	private static final long serialVersionUID = 1L;

}
