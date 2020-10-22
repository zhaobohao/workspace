package org.springclouddev.system.feign;

import org.springclouddev.core.tool.api.R;
import org.springclouddev.system.entity.Dict;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 * @author zhaobohao
 */
@Component
public class IDictClientFallback implements IDictClient {
	@Override
	public R<String> getValue(String code, String dictKey) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<Dict>> getList(String code) {
		return R.fail("获取数据失败");
	}
}
