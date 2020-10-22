package org.springclouddev.desk.feign;

import org.springclouddev.core.launch.constant.AppConstant;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.desk.entity.Notice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Notice Feign接口类
 *
 * @author zhaobohao
 */
@FeignClient(
	value = AppConstant.APPLICATION_DESK_NAME
)
public interface INoticeClient {

	String API_PREFIX = "/dashboard";

	/**
	 * 获取notice列表
	 *
	 * @param number
	 * @return
	 */
	@GetMapping(API_PREFIX + "/top")
	R<List<Notice>> top(@RequestParam("number") Integer number);

}
