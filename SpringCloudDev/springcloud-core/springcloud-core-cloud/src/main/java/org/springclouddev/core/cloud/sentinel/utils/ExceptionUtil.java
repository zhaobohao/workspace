package org.springclouddev.core.cloud.sentinel.utils;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class ExceptionUtil {
	// Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
	public String exceptionHandler(String s, BlockException ex) {
		// Do some log here.
		ex.printStackTrace();
		return "Oops, error occurred at " + s;
	}
}
