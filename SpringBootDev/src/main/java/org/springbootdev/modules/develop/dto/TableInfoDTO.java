
package org.springbootdev.modules.develop.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springbootdev.modules.develop.entity.TableInfo;

/**
 * 数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TableInfoDTO extends TableInfo {
	private static final long serialVersionUID = 1L;

}
