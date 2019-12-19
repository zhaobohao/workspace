
package org.springbootdev.modules.develop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springbootdev.core.boot.ctrl.AbstractController;
import org.springbootdev.core.launch.constant.AppConstant;
import org.springbootdev.core.mp.support.Condition;
import org.springbootdev.core.mp.support.Query;
import org.springbootdev.core.secure.SystemUser;
import org.springbootdev.core.tool.api.R;
import org.springbootdev.core.tool.constant.ToolConstant;
import org.springbootdev.core.tool.utils.Func;
import org.springbootdev.modules.develop.entity.DbInstance;
import org.springbootdev.modules.develop.service.IDbInstanceService;
import org.springbootdev.modules.develop.vo.DbInstanceVO;
import org.springbootdev.modules.develop.wrapper.DbInstanceWrapper;
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
@RequestMapping("/"+AppConstant.APPLICATION_DEVELOP_NAME +"/dbinstance")
@Api(value = "", tags = "接口")
public class DbInstanceController extends AbstractController {

    private IDbInstanceService dbInstanceService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入dbInstance")
    public R<DbInstanceVO> detail(DbInstance dbInstance) {
        DbInstance detail = dbInstanceService.getOne(Condition.getQueryWrapper(dbInstance));
        return R.data(DbInstanceWrapper.build().entityVO(detail));
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入dbInstance")
    public R<IPage<DbInstanceVO>> list(DbInstance dbInstance, Query query) {
        IPage<DbInstance> pages = dbInstanceService.page(Condition.getPage(query), Condition.getQueryWrapper(dbInstance));
        return R.data(DbInstanceWrapper.build().pageVO(pages));
    }


    /**
     * 自定义分页
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入dbInstance")
    public R<IPage<DbInstanceVO>> page(DbInstanceVO dbInstance, Query query) {
        IPage<DbInstanceVO> pages = dbInstanceService.selectDbInstancePage(Condition.getPage(query), dbInstance);
        return R.data(pages);
    }

    /**
     * 列表
     */
    @GetMapping("/list/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "数据库名称", paramType = "query", dataType = "string"),
    })
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "列表", notes = "传入dbInstance")
    public R<IPage<DbInstance>> list(@ApiIgnore @RequestParam Map<String, Object> dbInstance, Query query, SystemUser systemUser) {
        QueryWrapper<DbInstance> queryWrapper = Condition.getQueryWrapper(dbInstance, DbInstance.class);
        IPage<DbInstance> pages = dbInstanceService.page(Condition.getPage(query), queryWrapper);
        return R.data(pages);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入dbInstance")
    public R save(@Valid @RequestBody DbInstance dbInstance) {
        return R.status(dbInstanceService.save(dbInstance));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入dbInstance")
    public R update(@Valid @RequestBody DbInstance dbInstance) {
        return R.status(dbInstanceService.updateById(dbInstance));
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入dbInstance")
    public R submit(@Valid @RequestBody DbInstance dbInstance) {
        if (dbInstanceService.saveOrUpdate(dbInstance)) {
            return R.data(dbInstance);
        } else {
            return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, dbInstance, ToolConstant.DEFAULT_FAILURE_MESSAGE);
        }
    }


    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(dbInstanceService.deleteLogic(Func.toLongList(ids)));
    }

    /**
     * 数据源列表
     */
    @GetMapping("/select")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "下拉数据源", notes = "查询列表")
    public R<List<DbInstance>> select() {
        List<DbInstance> list = dbInstanceService.list();
        return R.data(list);
    }
}
