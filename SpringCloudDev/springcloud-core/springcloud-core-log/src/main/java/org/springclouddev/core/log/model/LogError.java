package org.springclouddev.core.log.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 服务 异常
 *
 * @author zhaobohao
 */
@Data
@TableName("mk_log_error")
public class LogError extends LogAbstract implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 堆栈信息
	 */
	private String stackTrace;
	/**
	 * 异常名
	 */
	private String exceptionName;
	/**
	 * 异常消息
	 */
	private String message;

	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 代码行数
	 */
	private Integer lineNumber;


}
