
package org.springclouddev.integral.dto;

import org.springclouddev.integral.entity.ClientGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientGroupDTO extends ClientGroup {
	private static final long serialVersionUID = 1L;

}
