
package org.springbootdev.modules.develop.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springbootdev.core.boot.ctrl.AbstractController;
import org.springbootdev.core.boot.upload.service.UploadFileHandler;
import org.springbootdev.core.launch.constant.AppConstant;
import org.springbootdev.core.mp.support.Condition;
import org.springbootdev.core.mp.support.Query;
import org.springbootdev.core.secure.SystemUser;
import org.springbootdev.core.tool.api.R;
import org.springbootdev.core.tool.constant.ToolConstant;
import org.springbootdev.core.tool.utils.Func;
import org.springbootdev.core.tool.utils.SpringUtil;
import org.springbootdev.modules.develop.entity.TableInfo;
import org.springbootdev.modules.develop.service.ITableInfoService;
import org.springbootdev.modules.develop.vo.TableInfoVO;
import org.springbootdev.modules.develop.wrapper.TableInfoWrapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/" + AppConstant.APPLICATION_DEVELOP_NAME + "/tableinfo")
@Api(value = "表元数据", tags = "接口")
public class TableInfoController extends AbstractController {

	private ITableInfoService tableInfoService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tableInfo")
	public R<TableInfoVO> detail(TableInfo tableInfo) {
		TableInfo detail = tableInfoService.getOne(Condition.getQueryWrapper(tableInfo));
		return R.data(TableInfoWrapper.build().entityVO(detail));
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入tableInfo")
	public R<IPage<TableInfoVO>> list(TableInfo tableInfo, Query query) {
		IPage<TableInfo> pages = tableInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(tableInfo));
		return R.data(TableInfoWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入tableInfo")
	public R<IPage<TableInfoVO>> page(TableInfoVO tableInfo, Query query) {
		IPage<TableInfoVO> pages = tableInfoService.selectTableInfoPage(Condition.getPage(query), tableInfo);
		return R.data(pages);
	}

	/**
	 * 列表
	 */
	@GetMapping("/list/page")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "parentId", value = "父id", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "列表", notes = "传入tableInfo")
	public R<IPage<TableInfo>> list(@ApiIgnore @RequestParam Map<String, Object> tableInfo, Query query, SystemUser systemUser) {
		QueryWrapper<TableInfo> queryWrapper = Condition.getQueryWrapper(tableInfo, TableInfo.class);
		IPage<TableInfo> pages = tableInfoService.page(Condition.getPage(query), queryWrapper);
		return R.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增", notes = "传入tableInfo")
	public R save(@Valid @RequestBody TableInfo tableInfo) {
		return R.status(tableInfoService.save(tableInfo));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "修改", notes = "传入tableInfo")
	public R update(@Valid @RequestBody TableInfo tableInfo) {
		return R.status(tableInfoService.updateById(tableInfo));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "新增或修改", notes = "传入tableInfo")
	public R submit(@Valid @RequestBody TableInfo tableInfo) {
		if (tableInfoService.saveOrUpdate(tableInfo)) {
			return R.data(tableInfo);
		} else {
			return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, tableInfo, ToolConstant.DEFAULT_FAILURE_MESSAGE);
		}
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tableInfoService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 数据源列表
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "下拉数据源", notes = "查询列表")
	public R<List<TableInfo>> select() {
		List<TableInfo> list = tableInfoService.list();
		return R.data(list);
	}


	/**
	 * 复制
	 */
	@PostMapping("/copy")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "复制", notes = "传入id")
	public R copy(@ApiParam(value = "主键", required = true) @RequestParam Integer id) {
		TableInfo tableInfo = tableInfoService.getById(id);
		tableInfo.setId(null);
		tableInfo.setName(tableInfo.getName() + "-copy");
		if (tableInfoService.saveOrUpdate(tableInfo)) {
			return R.data(tableInfo);
		} else {
			return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, tableInfo, ToolConstant.DEFAULT_FAILURE_MESSAGE);
		}
	}

	/**
	 * 获取菜单树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<TableInfoVO>> tree(String parentId) {
		List<TableInfoVO> tree = tableInfoService.tree(parentId);
		return R.data(tree);
	}

	/**
	 * 复制
	 */
	@PostMapping("/uploadExcel")
	@ApiOperationSupport(order = 6)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "dbInstanceId", value = "父数据库id", paramType = "params", dataType = "string"),
	})
	@ApiOperation(value = "上传excel", notes = "传入id")
	public Map<String, Object> y(@ApiParam(value = "上传的文件", required = true) @RequestParam MultipartFile upfile,@ApiIgnore @RequestParam Map<String, String> params ) throws Exception {
		UploadFileHandler handler= SpringUtil.getBean(params.get("uploadFileHandler"));
		handler.handler(upfile,params);
		// 返回报文给上传组件
		Map<String, Object> result = new HashMap<>();
		result.put("message", "");
		result.put("needMerge", false);
		result.put("result", true);
		result.put("uploaded", "[]");
		result.put("timestamp", "" + System.currentTimeMillis());
		return result;
	}

	/**
	 * 在上传文件前，会接收到一个查询当前文件上传情况的消息
	 */
	@GetMapping("/uploadExcel")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "上传excel", notes = "传入id")
	public Map<String, String> copys(Map<String, String> jobDetails) {
		log.info(jobDetails.toString());
		Map<String, String> result = new HashMap<>();
		result.put("message", "");
		result.put("needMerge", "");
		result.put("result", "true");
		result.put("uploaded", "[]");
		result.put("timestamp", "" + System.currentTimeMillis());
		return result;
	}
}
