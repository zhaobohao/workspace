package org.springclouddev.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springclouddev.core.boot.ctrl.AbstractController;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.constant.ToolConstant;
import org.springclouddev.core.tool.node.INode;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.system.entity.Dict;
import org.springclouddev.system.service.IDictService;
import org.springclouddev.system.vo.DictVO;
import org.springclouddev.system.wrapper.DictWrapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static org.springclouddev.common.cache.CacheNames.DICT_LIST;
import static org.springclouddev.common.cache.CacheNames.DICT_VALUE;

/**
 * 控制器
 *
 * @author zhaobohao
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dict")
@Api(value = "字典", tags = "字典")
public class DictController extends AbstractController {

    private IDictService dictService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入dict")
    public R<DictVO> detail(Dict dict) {
        Dict detail = dictService.getOne(Condition.getQueryWrapper(dict));
        return R.data(DictWrapper.build().entityVO(detail));
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string")
    })
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "列表", notes = "传入dict")
    public R<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> dict) {
        @SuppressWarnings("unchecked")
        List<Dict> list = dictService.list(Condition.getQueryWrapper(dict, Dict.class).lambda().orderByAsc(Dict::getSort));
        return R.data(DictWrapper.build().listNodeVO(list));
    }

    /**
     * 分页
     */
    @GetMapping("/list/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "parentId_equal", value = "父id", paramType = "query", dataType = "string")
    })
    @ApiOperation(value = "分页", notes = "传入tenant")
    public R<IPage<Dict>> list(@ApiIgnore @RequestParam Map<String, Object> dict, Query query) {
        QueryWrapper<Dict> queryWrapper = Condition.getQueryWrapper(dict, Dict.class);
        IPage<Dict> pages = dictService.page(Condition.getPage(query), queryWrapper);
        return R.data(pages);
    }

    /**
     * 获取字典树形结构
     *
     * @return
     */
    @GetMapping("/tree")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "树形结构", notes = "树形结构")
    public R<List<DictVO>> tree(String parentId) {
        List<DictVO> tree = dictService.tree(parentId);
        return R.data(tree);
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增或修改", notes = "传入dict")
    public R submit(@Valid @RequestBody Dict dict) {
        if (dictService.submit(dict)) {
            return R.data(dict);
        } else {
            return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, dict, ToolConstant.DEFAULT_FAILURE_MESSAGE);
        }
    }


    /**
     * 删除
     */
    @PostMapping("/remove")
    @CacheEvict(cacheNames = {DICT_LIST, DICT_VALUE}, allEntries = true)
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(dictService.removeByIds(Func.toLongList(ids)));
    }

    /**
     * 获取字典
     *
     * @return
     */
    @GetMapping("/dictionary")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "获取字典", notes = "获取字典")
    public R<List<Dict>> dictionary(String code) {
        List<Dict> tree = dictService.getList(code);
        return R.data(tree);
    }


}
