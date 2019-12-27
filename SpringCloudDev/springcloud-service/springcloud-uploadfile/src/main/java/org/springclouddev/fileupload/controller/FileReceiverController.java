package org.springclouddev.fileupload.controller;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.boot.upload.service.UploadFileHandler;
import org.springclouddev.core.tool.utils.SpringUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件接收控制器
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/upload")
@Api(value = "接口", tags = "接口")
public class FileReceiverController {
    /**
     * 复制
     */
    @PostMapping("/file")
    @ApiOperationSupport(order = 6)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uploadFileHandler", value = "要处理文件的类ID", paramType = "params", dataType = "string"),
    })
    @ApiOperation(value = "上传文件，支持断点续传", notes = "传入文件")
    public Map<String, Object> y(@ApiParam(value = "上传的文件", required = true) @RequestParam MultipartFile upfile, @ApiIgnore @RequestParam Map<String, String> params ) throws Exception {
        UploadFileHandler handler= SpringUtil.getBean(params.get("uploadFileHandler"));
        handler.handler(upfile,params);
        // 返回报文给上传组件
        Map<String, Object> result = new HashMap<>();
        result.put("message", "");
        result.put("needMerge", false);
        result.put("result", true);
        result.put("uploaded", "[]");
        result.put("timestamp", "" + System.currentTimeMillis());
        return result;
    }

    /**
     * 在上传文件前，会接收到一个查询当前文件上传情况的消息
     */
    @GetMapping("/file")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "上传文件预先校验", notes = "传入上传文件分片参数")
    public Map<String, String> copys(Map<String, String> jobDetails) {
        log.info(jobDetails.toString());
        Map<String, String> result = new HashMap<>();
        result.put("message", "");
        result.put("needMerge", "");
        result.put("result", "true");
        result.put("uploaded", "[]");
        result.put("timestamp", "" + System.currentTimeMillis());
        return result;
    }
}
