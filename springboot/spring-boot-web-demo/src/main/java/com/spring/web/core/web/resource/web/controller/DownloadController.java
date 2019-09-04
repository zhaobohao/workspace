

package com.spring.web.core.web.resource.web.controller;

import com.spring.web.config.SpringBootPlusProperties;
import com.spring.web.core.api.ApiResult;
import com.spring.web.core.util.DownloadUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 下载控制器
 * @author geekidea
 * @date 2019/8/20
 * @since 1.2.1-RELEASE
 */
@Slf4j
@Controller
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private SpringBootPlusProperties springBootPlusProperties;

    /**
     * 下载文件
     */
    @RequestMapping("/{downloadFileName}")
    @ApiOperation(value = "下载文件",notes = "下载文件",response = ApiResult.class)
    public void download(@PathVariable(required = true) String downloadFileName, HttpServletResponse response) throws Exception{
        // 下载目录，既是上传目录
        String downloadDir = springBootPlusProperties.getUploadPath();
        // 允许下载的文件后缀
        List<String> allowFileExtensions = springBootPlusProperties.getAllowDownloadFileExtensions();
        // 文件下载，使用默认下载处理器
//        DownloadUtil.download(downloadDir,downloadFileName,allowFileExtensions,response);
        // 文件下载，使用自定义下载处理器
        DownloadUtil.download(downloadDir,downloadFileName,allowFileExtensions,response, (dir, fileName, file, fileExtension, contentType, length) -> {
            // 下载自定义处理，返回true：执行下载，false：取消下载
            log.info("dir = " + dir);
            log.info("fileName = " + fileName);
            log.info("file = " + file);
            log.info("fileExtension = " + fileExtension);
            log.info("contentType = " + contentType);
            log.info("length = " + length);
            return true;
        });
    }

}
