package org.springclouddev.core.log.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springclouddev.core.log.model.LogApi;
import org.springclouddev.core.log.model.LogApiVo;
import org.springclouddev.core.log.service.ILogApiService;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.utils.BeanUtil;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.core.tool.utils.StringPool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 控制器
 *
 * @author zhaobohao
 * @since 2018-09-26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class LogApiController {

	private ILogApiService logService;

	/**
	 * 查询单条
	 */
	@GetMapping("/detail")
	public R<LogApi> detail(LogApi log) {
		return R.data(logService.getOne(Condition.getQueryWrapper(log)));
	}

	/**
	 * 查询多条(分页)
	 */
	@GetMapping("/list")
	public R<IPage<LogApiVo>> list(@ApiIgnore @RequestParam Map<String, Object> log, Query query) {
		query.setAscs("create_time");
		query.setDescs(StringPool.EMPTY);
		IPage<LogApi> pages = logService.page(Condition.getPage(query), Condition.getQueryWrapper(log, LogApi.class));
		List<LogApiVo> records = pages.getRecords().stream().map(logApi -> {
			LogApiVo vo = BeanUtil.copy(logApi, LogApiVo.class);
			vo.setStrId(Func.toStr(logApi.getId()));
			return vo;
		}).collect(Collectors.toList());
		IPage<LogApiVo> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
		pageVo.setRecords(records);
		return R.data(pageVo);
	}

}
