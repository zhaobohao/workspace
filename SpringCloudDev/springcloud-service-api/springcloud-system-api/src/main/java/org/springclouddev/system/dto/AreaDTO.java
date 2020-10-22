package org.springclouddev.system.dto;

import org.springclouddev.system.entity.Area;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 行政区划数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AreaDTO extends Area {
	private static final long serialVersionUID = 1L;

}
