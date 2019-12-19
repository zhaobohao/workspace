
package org.springclouddev.develop.dto;

import org.springclouddev.develop.entity.DbInstance;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DbInstanceDTO extends DbInstance {
	private static final long serialVersionUID = 1L;

}
