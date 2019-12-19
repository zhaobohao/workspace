
package org.springbootdev.modules.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springbootdev.core.launch.constant.AppConstant;
import org.springbootdev.core.log.model.LogError;
import org.springbootdev.core.log.model.LogErrorVo;
import org.springbootdev.core.mp.support.Condition;
import org.springbootdev.core.mp.support.Query;
import org.springbootdev.core.tool.api.R;
import org.springbootdev.core.tool.utils.BeanUtil;
import org.springbootdev.core.tool.utils.Func;
import org.springbootdev.modules.system.service.ILogErrorService;
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
 * @author merryChen
 */
@ApiIgnore
@RestController
@AllArgsConstructor
@RequestMapping("/"+AppConstant.APPLICATION_LOG_NAME +"/error")
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
		IPage<LogError> pages = errorLogService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(logError, LogError.class));
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
