
package org.springclouddev.develop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springclouddev.core.boot.ctrl.AbstractController;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.core.secure.annotation.PreAuth;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.constant.RoleConstant;
import org.springclouddev.core.tool.constant.ToolConstant;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.develop.entity.Code;
import org.springclouddev.develop.entity.Datasource;
import org.springclouddev.develop.service.ICodeService;
import org.springclouddev.develop.service.IDatasourceService;
import org.springclouddev.develop.support.SpringCloudDEmoCodeGenerator;
import org.springclouddev.develop.templateengine.VelocityTemplateZipEngine;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 控制器
 *
 * @author zhaobohao
 */
@ApiIgnore
@RestController
@AllArgsConstructor
@RequestMapping("/code")
@Api(value = "代码生成", tags = "代码生成")
@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
public class AbstractBaseController extends AbstractController {

    private ICodeService codeService;
    private IDatasourceService datasourceService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入code")
    public R<Code> detail(Code code) {
        Code detail = codeService.getOne(Condition.getQueryWrapper(code));
        return R.data(detail);
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeName", value = "模块名", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableName", value = "表名", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "modelName", value = "实体名", paramType = "query", dataType = "string")
    })
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入code")
    public R<IPage<Code>> list(@ApiIgnore @RequestParam Map<String, Object> code, Query query) {
        IPage<Code> pages = codeService.page(Condition.getPage(query), Condition.getQueryWrapper(code, Code.class));
        return R.data(pages);
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增或修改", notes = "传入code")
    public R submit(@Valid @RequestBody Code code) {
        if (codeService.saveOrUpdate(code)) {
            return R.data(code);
        } else {
            return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, code, ToolConstant.DEFAULT_FAILURE_MESSAGE);
        }
    }


    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(codeService.removeByIds(Func.toLongList(ids)));
    }

    /**
     * 复制
     */
    @PostMapping("/copy")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "复制", notes = "传入id")
    public R copy(@ApiParam(value = "主键", required = true) @RequestParam Integer id) {
        Code code = codeService.getById(id);
        code.setId(null);
        code.setCodeName(code.getCodeName() + "-copy");
        if (codeService.saveOrUpdate(code)) {
            return R.data(code);
        } else {
            return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, code, ToolConstant.DEFAULT_FAILURE_MESSAGE);
        }
    }

    /**
     * 代码生成
     */
    @PostMapping("/gen-code")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "代码生成", notes = "传入ids")
    public void genCode(@ApiParam(value = "主键集合", required = true) @RequestParam String ids, @RequestParam(defaultValue = "vue element admin") String system, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Collection<Code> codes = codeService.listByIds(Func.toLongList(ids));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (
                ZipOutputStream zip = new ZipOutputStream(outputStream);) {
            codes.forEach(code -> {
                SpringCloudDEmoCodeGenerator generator = new SpringCloudDEmoCodeGenerator();
                // 设置数据源
                Datasource datasource = datasourceService.getById(code.getDatasourceId());
                generator.setDriverName(datasource.getDriverClass());
                generator.setUrl(datasource.getUrl());
                generator.setUsername(datasource.getUsername());
                generator.setPassword(datasource.getPassword());
                // 设置基础配置
                generator.setSystemName(system);
                generator.setServiceName(code.getServiceName());
                generator.setPackageName(code.getPackageName());
                generator.setPackageDir(code.getApiPath());
                generator.setPackageWebDir(code.getWebPath());
                generator.setTablePrefix(Func.toStrArray(code.getTablePrefix()));
                generator.setIncludeTables(Func.toStrArray(code.getTableName()));
                // 设置是否继承基础业务字段
                generator.setHasSuperEntity(code.getBaseMode() == 2);
                // 设置是否开启包装器模式
                generator.setHasWrapper(code.getWrapMode() == 2);
                // 设置新的模板引擎，直接将生成的文件流通过流的形式输出到web系统。
                generator.setTemplateEngine(new VelocityTemplateZipEngine(zip));
                generator.run();
            });
        }
        // 将zip数据流输出到页面，并通过SaveAs的js代码完成下载
        byte[] data = outputStream.toByteArray();
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"auto-code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
