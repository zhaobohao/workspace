
package org.springclouddev.develop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springclouddev.core.boot.ctrl.AbstractController;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.core.secure.SystemUser;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.constant.ToolConstant;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.develop.entity.TableInfo;
import org.springclouddev.develop.service.ITableInfoService;
import org.springclouddev.develop.vo.TableInfoVO;
import org.springclouddev.develop.wrapper.TableInfoWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
@RequestMapping("/tableinfo")
@Api(value = "", tags = "接口")
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

}
