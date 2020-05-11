package org.springclouddev.mockserver.controller;

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
import org.springclouddev.mockserver.config.Constants;
import org.springclouddev.mockserver.config.MockServerInit;
import org.springclouddev.mockserver.entity.MockHttp;
import org.springclouddev.mockserver.service.IMockHttpService;
import org.springclouddev.mockserver.vo.MockHttpVO;
import org.springclouddev.mockserver.wrapper.MockHttpWrapper;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.data.redis.core.RedisTemplate;
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
@RequestMapping("/mockhttp")
@Api(value = "", tags = "接口")
public class MockHttpController extends AbstractController {

    private IMockHttpService mockHttpService;

    private MockServerInit mockServerInit;

    private RedisTemplate j2CacheRedisTemplate;

    private ServerProperties serverProperties;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入mockHttp")
    public R<MockHttpVO> detail(MockHttp mockHttp) {
        MockHttp detail = mockHttpService.getOne(Condition.getQueryWrapper(mockHttp));
        return R.data(MockHttpWrapper.build().entityVO(detail));
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入mockHttp")
    public R<IPage<MockHttpVO>> list(MockHttp mockHttp, Query query) {
        IPage<MockHttp> pages = mockHttpService.page(Condition.getPage(query), Condition.getQueryWrapper(mockHttp));
        return R.data(MockHttpWrapper.build().pageVO(pages));
    }


    /**
     * 自定义分页
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "分页", notes = "传入mockHttp")
    public R<IPage<MockHttpVO>> page(MockHttpVO mockHttp, Query query) {
        IPage<MockHttpVO> pages = mockHttpService.selectMockHttpPage(Condition.getPage(query), mockHttp);
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
    @ApiOperation(value = "列表", notes = "传入mockHttp")
    public R<IPage<MockHttp>> list(@ApiIgnore @RequestParam Map<String, Object> mockHttp, Query query, SystemUser systemUser) {
        QueryWrapper<MockHttp> queryWrapper = Condition.getQueryWrapper(mockHttp, MockHttp.class);
        IPage<MockHttp> pages = mockHttpService.page(Condition.getPage(query), queryWrapper);
        return R.data(pages);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "新增", notes = "传入mockHttp")
    public R save(@Valid @RequestBody MockHttp mockHttp) {
        return R.status(mockHttpService.save(mockHttp));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "修改", notes = "传入mockHttp")
    public R update(@Valid @RequestBody MockHttp mockHttp) {
        return R.status(mockHttpService.updateById(mockHttp));
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "新增或修改", notes = "传入mockHttp")
    public R submit(@Valid @RequestBody MockHttp mockHttp) {
        //由于正式上线使用时，会分布式部署，这里必须要实现redis的订阅发布功能，通知其它的mockserver更新接口
        if (mockHttp.getId() != null) {
            mockHttp.setWebSiteId(null);
            j2CacheRedisTemplate.convertAndSend(Constants.CHANNEL_MOCK_SERVER_CLEAR, mockHttp.getId());
            try {
                //等待清理事件完成。
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (mockHttpService.saveOrUpdate(mockHttp)) {
            j2CacheRedisTemplate.convertAndSend(Constants.CHANNEL_MOCK_SERVER_RECEIVE, mockHttp.getId());
            return R.data(mockHttp);
        } else {
            return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, mockHttp, ToolConstant.DEFAULT_FAILURE_MESSAGE);
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
            j2CacheRedisTemplate.convertAndSend(Constants.CHANNEL_MOCK_SERVER_CLEAR, id);
        }
        try {
            //等待清理事件完成。
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return R.status(mockHttpService.deleteLogic(Func.toLongList(ids)));
    }

    /**
     * 数据源列表
     */
    @GetMapping("/select")
    @ApiOperationSupport(order = 12)
    @ApiOperation(value = "下拉数据源", notes = "查询列表")
    public R<List<MockHttp>> select() {
        List<MockHttp> list = mockHttpService.list();
        return R.data(list);
    }


}
