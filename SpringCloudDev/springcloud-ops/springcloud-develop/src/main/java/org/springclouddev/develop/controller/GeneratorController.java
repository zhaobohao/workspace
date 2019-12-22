package org.springclouddev.develop.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springclouddev.develop.service.IGeneratorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/generator")
@AllArgsConstructor
@Api(value = "代码生成器", tags = "生成相关代码，并提供zip包下载")
@Slf4j
public class GeneratorController {
    private IGeneratorService generatorService;
    /**
     * 获取菜单树形结构
     */
    @GetMapping("/exportDdl")
    @ApiOperationSupport(order = 12)
    @ApiOperation(value = "导出DDL文件", notes = "导出DDL文件")
    public void exportDdl(@ApiParam(value = "主键集合", required = true) @RequestParam String ids , HttpServletRequest request, HttpServletResponse response) throws Exception {

        byte[] data = generatorService.generatorDdlFile(ids);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"ddl.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }

}
