package org.springclouddev.mockserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.boot.ctrl.AbstractController;
import org.springclouddev.core.launch.constant.AppConstant;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.core.secure.SystemUser;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.constant.ToolConstant;
import org.springclouddev.core.tool.node.TreeNode;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.mockserver.entity.MockWebSite;
import org.springclouddev.mockserver.service.IMockWebSiteService;
import org.springclouddev.mockserver.vo.MockWebSiteVO;
import org.springclouddev.mockserver.wrapper.MockWebSiteWrapper;
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
 * @since 2020-04-07
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/mockwebsite")
@Api(value = "", tags = "接口")
public class MockWebSiteController extends AbstractController {

    private IMockWebSiteService mockWebSiteService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入mockWebSite")
    public R<MockWebSiteVO> detail(MockWebSite mockWebSite) {
        MockWebSite detail = mockWebSiteService.getOne(Condition.getQueryWrapper(mockWebSite));
        return R.data(MockWebSiteWrapper.build().entityVO(detail));
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入mockWebSite")
    public R<IPage<MockWebSiteVO>> list(Map<String,Object> mockWebSite, Query query) {
        IPage<MockWebSite> pages = mockWebSiteService.page(Condition.getPage(query), Condition.getQueryWrapper(mockWebSite,MockWebSite.class));
        return R.data(MockWebSiteWrapper.build().pageVO(pages));
    }


    /**
     * 自定义分页
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "分页", notes = "传入mockWebSite")
    public R<IPage<MockWebSiteVO>> page(MockWebSiteVO mockWebSite, Query query) {
        IPage<MockWebSiteVO> pages = mockWebSiteService.selectMockWebSitePage(Condition.getPage(query), mockWebSite);
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
    @ApiOperation(value = "列表", notes = "传入mockWebSite")
    public R<IPage<MockWebSite>> list(@ApiIgnore @RequestParam Map<String, Object> mockWebSite, Query query, SystemUser systemUser) {
        QueryWrapper<MockWebSite> queryWrapper = Condition.getQueryWrapper(mockWebSite, MockWebSite.class);
        IPage<MockWebSite> pages = mockWebSiteService.page(Condition.getPage(query), queryWrapper);
        return R.data(pages);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "新增", notes = "传入mockWebSite")
    public R save(@Valid @RequestBody MockWebSite mockWebSite) {
        return R.status(mockWebSiteService.save(mockWebSite));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "修改", notes = "传入mockWebSite")
    public R update(@Valid @RequestBody MockWebSite mockWebSite) {
        return R.status(mockWebSiteService.updateById(mockWebSite));
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "新增或修改", notes = "传入mockWebSite")
    public R submit(@Valid @RequestBody MockWebSite mockWebSite) {
        if (mockWebSiteService.saveOrUpdate(mockWebSite)) {
            return R.data(mockWebSite);
        } else {
            return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, mockWebSite, ToolConstant.DEFAULT_FAILURE_MESSAGE);
        }
    }


    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(mockWebSiteService.deleteLogic(Func.toLongList(ids)));
    }

    /**
     * 数据源列表
     */
    @GetMapping("/select")
    @ApiOperationSupport(order = 12)
    @ApiOperation(value = "下拉数据源", notes = "查询列表")
    public R<List<MockWebSite>> select() {
        List<MockWebSite> list = mockWebSiteService.list();
        return R.data(list);
    }
    /**
     * 获取菜单树形结构
     */
    @GetMapping("/tree")
    @ApiOperationSupport(order = 11)
    @ApiOperation(value = "树形结构", notes = "树形结构")
    public R<List<TreeNode>> tree() {
        List<TreeNode> tree = mockWebSiteService.tree();
        return R.data(tree);
    }

}
