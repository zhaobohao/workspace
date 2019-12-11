
package org.springclouddev.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springclouddev.core.boot.ctrl.AbstractController;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.constant.ToolConstant;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.system.entity.Param;
import org.springclouddev.system.service.IParamService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

/**
 * 控制器
 *
 * @author zhaobohao
 */
@RestController
@AllArgsConstructor
@RequestMapping("/param")
@Api(value = "参数管理", tags = "接口")
public class ParamController extends AbstractController {

	private IParamService paramService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入param")
	public R<Param> detail(Param param) {
		Param detail = paramService.getOne(Condition.getQueryWrapper(param));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "paramName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "paramKey", value = "参数键名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "paramValue", value = "参数键值", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入param")
	public R<IPage<Param>> list(@ApiIgnore @RequestParam Map<String, Object> param, Query query) {
		IPage<Param> pages = paramService.page(Condition.getPage(query), Condition.getQueryWrapper(param, Param.class));
		return R.data(pages);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增或修改", notes = "传入param")
	public R submit(@Valid @RequestBody Param param){

		if (paramService.saveOrUpdate(param)) {
			return R.data(param);
		} else {
			return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, param, ToolConstant.DEFAULT_FAILURE_MESSAGE);
		}
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(paramService.deleteLogic(Func.toLongList(ids)));
	}


}
