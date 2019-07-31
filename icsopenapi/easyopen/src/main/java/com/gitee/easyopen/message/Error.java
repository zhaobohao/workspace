package com.gitee.easyopen.message;

/**
 * 定义错误返回
 * @author tanghc
 *
 * @param <T> 状态码类型，一般为Integer或String
 */
public interface Error<T> {
	/**
	 * 获取错误信息
	 * @return 返回错误信息
	 */
	String getMsg();
	
	/**
	 * 获取状态码
	 * @return 返回状态码
	 */
	T getCode();
}
