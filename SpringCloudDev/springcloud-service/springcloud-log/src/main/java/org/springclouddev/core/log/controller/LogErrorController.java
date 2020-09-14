
package org.springclouddev.core.log.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springclouddev.core.log.model.LogError;
import org.springclouddev.core.log.model.LogErrorVo;
import org.springclouddev.core.log.service.ILogErrorService;
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
@RequestMapping("/error")
public class LogErrorController {

	private ILogErrorService errorLogService;

	/**
	 * 查询单条
	 */
	@GetMapping("/detail")
	public R<LogError> detail(LogError logError) {
		return R.data(errorLogService.getOne(Condition.getQueryWrapper(logError)));
	}

	/**
	 * 查询多条(分页)
	 */
	@GetMapping("/list")
	public R<IPage<LogErrorVo>> list(@ApiIgnore @RequestParam Map<String, Object> logError, Query query) {
		query.setAscs("create_time");
		query.setDescs(StringPool.EMPTY);
		IPage<LogError> pages = errorLogService.page(Condition.getPage(query), Condition.getQueryWrapper(logError, LogError.class));
		List<LogErrorVo> records = pages.getRecords().stream().map(logApi -> {
			LogErrorVo vo = BeanUtil.copy(logApi, LogErrorVo.class);
			vo.setStrId(Func.toStr(logApi.getId()));
			return vo;
		}).collect(Collectors.toList());
		IPage<LogErrorVo> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
		pageVo.setRecords(records);
		return R.data(pageVo);
	}

}
