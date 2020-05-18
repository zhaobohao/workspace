package org.springbootdev.modules.drools.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springbootdev.core.boot.ctrl.AbstractController;
import org.springbootdev.core.mp.support.Condition;
import org.springbootdev.core.mp.support.Query;
import org.springbootdev.core.secure.SystemUser;
import org.springbootdev.core.tool.api.R;
import org.springbootdev.core.tool.constant.ToolConstant;
import org.springbootdev.core.tool.utils.Func;
import org.springbootdev.modules.drools.entity.DroolsRuls;
import org.springbootdev.modules.drools.jms.RedissonPublish;
import org.springbootdev.modules.drools.service.IDroolsRulsService;
import org.springbootdev.modules.drools.vo.DroolsRulsVO;
import org.springbootdev.modules.drools.wrapper.DroolsRulsWrapper;
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
 * @since 2020-05-11
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/droolsruls")
@Api(value = "", tags = "接口")
public class DroolsRulsController extends AbstractController {

    private IDroolsRulsService droolsRulsService;

    private RedissonPublish redissonPublish;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入droolsRuls")
    public R<DroolsRulsVO> detail(DroolsRuls droolsRuls) {
        DroolsRuls detail = droolsRulsService.getOne(Condition.getQueryWrapper(droolsRuls));
        return R.data(DroolsRulsWrapper.build().entityVO(detail));
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入droolsRuls")
    public R<IPage<DroolsRulsVO>> list(DroolsRuls droolsRuls, Query query) {
        IPage<DroolsRuls> pages = droolsRulsService.page(Condition.getPage(query), Condition.getQueryWrapper(droolsRuls));
        return R.data(DroolsRulsWrapper.build().pageVO(pages));
    }


    /**
     * 自定义分页
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "分页", notes = "传入droolsRuls")
    public R<IPage<DroolsRulsVO>> page(DroolsRulsVO droolsRuls, Query query) {
        IPage<DroolsRulsVO> pages = droolsRulsService.selectDroolsRulsPage(Condition.getPage(query), droolsRuls);
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
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "列表", notes = "传入droolsRuls")
    public R<IPage<DroolsRuls>> list(@ApiIgnore @RequestParam Map<String, Object> droolsRuls, Query query, SystemUser systemUser) {
        QueryWrapper<DroolsRuls> queryWrapper = Condition.getQueryWrapper(droolsRuls, DroolsRuls.class);
        IPage<DroolsRuls> pages = droolsRulsService.page(Condition.getPage(query), queryWrapper);
        return R.data(pages);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "新增", notes = "传入droolsRuls")
    public R save(@Valid @RequestBody DroolsRuls droolsRuls) {
        return R.status(droolsRulsService.save(droolsRuls));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "修改", notes = "传入droolsRuls")
    public R update(@Valid @RequestBody DroolsRuls droolsRuls) {
        return R.status(droolsRulsService.updateById(droolsRuls));
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "新增或修改", notes = "传入droolsRuls")
    public R submit(@Valid @RequestBody DroolsRuls droolsRuls) {
        if(droolsRuls.getId()!=null){
            droolsRuls.setGroupId(null);
            redissonPublish.publishClearTopic(String.valueOf(droolsRuls.getId()));
        try {
                //等待清理事件完成。
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (droolsRulsService.saveOrUpdate(droolsRuls)) {
            redissonPublish.publishReceiveTopic(String.valueOf(droolsRuls.getId()));
            return R.data(droolsRuls);
        } else {
            return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, droolsRuls, ToolConstant.DEFAULT_FAILURE_MESSAGE);
        }
    }


    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        for (String id : ids.split(",")) {
            redissonPublish.publishClearTopic(String.valueOf(id));
        }
        try {
            //等待清理事件完成。
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return R.status(droolsRulsService.deleteLogic(Func.toLongList(ids)));
    }

    /**
     * 数据源列表
     */
    @GetMapping("/select")
    @ApiOperationSupport(order = 12)
    @ApiOperation(value = "下拉数据源", notes = "查询列表")
    public R<List<DroolsRuls>> select() {
        List<DroolsRuls> list = droolsRulsService.list();
        return R.data(list);
    }


}
