package org.springclouddev.core.log.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author zhaobohao
 */
@Data
@TableName("mk_log_api")
public class LogApi extends LogAbstract implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 日志类型
	 */
	private String type;
	/**
	 * 日志标题
	 */
	private String title;

}
