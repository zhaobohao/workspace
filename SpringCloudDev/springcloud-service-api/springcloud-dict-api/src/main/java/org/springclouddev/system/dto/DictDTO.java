
package org.springclouddev.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springclouddev.system.entity.Dict;

/**
 * 数据传输对象实体类
 *
 * @author firewan
 * @since 2018-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictDTO extends Dict {
	private static final long serialVersionUID = 1L;

}
