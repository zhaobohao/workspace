package org.springclouddev.desk.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.support.Kv;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 *
 * @author zhaobohao
 */
@RestController
@RequestMapping("dashboard")
@AllArgsConstructor
@Api(value = "首页", tags = "首页")
public class DashBoardController {

	/**
	 * 活跃用户
	 *
	 * @return
	 */
	@GetMapping("/activities")
	@ApiOperation(value = "活跃用户", notes = "活跃用户")
	public R activities() {

		List<Map<String, Object>> list = new ArrayList<>();
		return R.data(list);
	}
}
