
package org.springclouddev.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springclouddev.core.boot.ctrl.AbstractController;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.core.secure.SystemUser;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.constant.RoleConstant;
import org.springclouddev.core.tool.constant.ToolConstant;
import org.springclouddev.core.tool.node.INode;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.system.entity.Dept;
import org.springclouddev.system.service.IDeptService;
import org.springclouddev.system.vo.DeptVO;
import org.springclouddev.system.wrapper.DeptWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author zhaobohao
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dept")
@Api(value = "部门", tags = "部门")
public class DeptController extends AbstractController {

    private IDeptService deptService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入dept")
    public R<DeptVO> detail(Dept dept) {
        Dept detail = deptService.getOne(Condition.getQueryWrapper(dept));
        return R.data(DeptWrapper.build().entityVO(detail));
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptName", value = "部门名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "fullName", value = "部门全称", paramType = "query", dataType = "string")
    })
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "列表", notes = "传入dept")
    public R<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> dept, SystemUser systemUser) {
        QueryWrapper<Dept> queryWrapper = Condition.getQueryWrapper(dept, Dept.class);
        List<Dept> list = deptService.list((!systemUser.getTenantId().equals(ToolConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Dept::getTenantId, systemUser.getTenantId()) : queryWrapper);
        return R.data(DeptWrapper.build().listNodeVO(list));
    }

    /**
     * 分页
     */
    @GetMapping("/list/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptName", value = "参数名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "fullName", value = "角色别名", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "parentId", value = "父id", paramType = "query", dataType = "string")
    })
    @ApiOperation(value = "分页", notes = "传入tenant")
    public R<IPage<Dept>> list(@ApiIgnore @RequestParam Map<String, Object> dept, Query query, SystemUser systemUser) {
        QueryWrapper<Dept> queryWrapper = Condition.getQueryWrapper(dept, Dept.class);
        IPage<Dept> pages = deptService.page(Condition.getPage(query), (!systemUser.getTenantId().equals(ToolConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Dept::getTenantId, systemUser.getTenantId()) : queryWrapper);
        return R.data(pages);
    }

    /**
     * 获取部门树形结构
     *
     * @return
     */
    @GetMapping("/tree")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "树形结构", notes = "树形结构")
    public R<List<DeptVO>> tree(String tenantId, String parentId, SystemUser systemUser) {
        List<DeptVO> tree = new ArrayList<DeptVO>();
        if (RoleConstant.HAS_ROLE_ADMIN.equals(systemUser.getRoleName())) {
            tree = deptService.tree(null, parentId);
        } else {
            tree = deptService.tree(Func.toStr(tenantId, systemUser.getTenantId()), parentId);
        }
        return R.data(tree);
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增或修改", notes = "传入dept")
    public R submit(@Valid @RequestBody Dept dept, SystemUser user) {
        if (Func.isEmpty(dept.getId())) {
            dept.setTenantId(user.getTenantId());
        }

        if (deptService.submit(dept)) {
            return R.data(dept);
        } else {
            return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, dept, ToolConstant.DEFAULT_FAILURE_MESSAGE);
        }
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(deptService.removeByIds(Func.toIntList(ids)));
    }


}
