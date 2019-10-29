
package org.springbootdev.core.log.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author zhaobohao
 * @since 2018-10-12
 */
@Data
@TableName("mk_log_usual")
public class LogUsual extends LogAbstract implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 日志级别
	 */
	private String logLevel;
	/**
	 * 日志业务id
	 */
	private String logId;
	/**
	 * 日志数据
	 */
	private String logData;


}
