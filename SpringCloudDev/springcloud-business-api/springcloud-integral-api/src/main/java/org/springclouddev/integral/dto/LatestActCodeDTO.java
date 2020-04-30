
package org.springclouddev.integral.dto;

import org.springclouddev.integral.entity.LatestActCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 最新活动积分编号表数据传输对象实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LatestActCodeDTO extends LatestActCode {
	private static final long serialVersionUID = 1L;

}
