package org.springclouddev.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.boot.ctrl.AbstractController;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.core.secure.SystemUser;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.constant.ToolConstant;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.system.entityst;
import org.springclouddev.system.service.IPostService;
import org.springclouddev.system.vo.PostVO;
import org.springclouddev.system.wrapper.PostWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 岗位表 控制器
 *
 * @author zhaobohao
 * @since 2020-05-23
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/post")
@Api(value = "岗位表", tags = "岗位表接口")
public class PostController extends AbstractController {

    private IPostService postService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入post")
    public R<PostVO> detail(Post post) {
        Post detail = postService.getOne(Condition.getQueryWrapper(post));
        return R.data(PostWrapper.build().entityVO(detail));
    }

    /**
     * 分页 岗位表
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入post")
    public R<IPage<PostVO>> list(Post post, Query query) {
        IPage<Post> pages = postService.page(Condition.getPage(query), Condition.getQueryWrapper(post));
        return R.data(PostWrapper.build().pageVO(pages));
    }


    /**
     * 自定义分页 岗位表
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "分页", notes = "传入post")
    public R<IPage<PostVO>> page(PostVO post, Query query) {
        IPage<PostVO> pages = postService.selectPostPage(Condition.getPage(query), post);
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
    @ApiOperation(value = "列表", notes = "传入post")
    public R<IPage<Post>> list(@ApiIgnore @RequestParam Map<String, Object> post, Query query, SystemUser systemUser) {
        QueryWrapper<Post> queryWrapper = Condition.getQueryWrapper(post, Post.class);
        IPage<Post> pages = postService.page(Condition.getPage(query), queryWrapper);
        return R.data(pages);
    }

    /**
     * 新增 岗位表
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "新增", notes = "传入post")
    public R save(@Valid @RequestBody Post post) {
        return R.status(postService.save(post));
    }

    /**
     * 修改 岗位表
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "修改", notes = "传入post")
    public R update(@Valid @RequestBody Post post) {
        return R.status(postService.updateById(post));
    }

    /**
     * 新增或修改 岗位表
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "新增或修改", notes = "传入post")
    public R submit(@Valid @RequestBody Post post) {
        if (postService.saveOrUpdate(post)) {
            return R.data(post);
        } else {
            return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, post, ToolConstant.DEFAULT_FAILURE_MESSAGE);
        }
    }


    /**
     * 删除 岗位表
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(postService.deleteLogic(Func.toLongList(ids)));
    }

    /**
     * 数据源列表
     */
    @GetMapping("/select")
    @ApiOperationSupport(order = 12)
    @ApiOperation(value = "下拉数据源", notes = "查询列表")
    public R<List<Post>> select() {
        List<Post> list = postService.list();
        return R.data(list);
    }


}
