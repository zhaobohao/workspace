package com.gitee.sop.storyweb.controller;

import com.gitee.sop.servercommon.annotation.ApiMapping;
import com.gitee.sop.servercommon.util.UploadUtil;
import com.gitee.sop.storyweb.controller.param.FileUploadParam;
import com.gitee.sop.storyweb.controller.param.FileUploadParam2;
import com.gitee.sop.storyweb.controller.result.FileUploadResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
/**
 * 演示文件上传
 *
 * @author tanghc
 */
@RestController
@Api(tags = "文件上传", position = 2)
public class FileUploadDemoController {

    /**
     * 方式1：将文件写在参数中，可直接获取。好处是可以校验是否上传
     * @param param
     * @return
     */
    @ApiOperation(value = "文件上传例1", notes = "上传文件demo")
    @ApiMapping(value = "demo.file.upload")
    public FileUploadResult file1(FileUploadParam param) {
        // 获取上传的文件
        MultipartFile file1 = param.getFile1();
        MultipartFile file2 = param.getFile2();

        FileUploadResult result = new FileUploadResult();
        FileUploadResult.FileMeta fileMeta1 = buildFileMeta(file1);
        FileUploadResult.FileMeta fileMeta2 = buildFileMeta(file2);

        result.setRemark(param.getRemark());
        result.getFiles().add(fileMeta1);
        result.getFiles().add(fileMeta2);
        return result;
    }

    /**
     * 方式2：从request中获取上传文件
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "文件上传例2", notes = "可上传多个文件"
            // 多文件上传、不确定文件数量上传，必须申明下面这句，否则沙盒界面不会出现上传控件
            , extensions = @Extension(properties = @ExtensionProperty(name = "multiple", value = "multiple")))
    @ApiMapping(value = "demo.file.upload2")
    public FileUploadResult file2(FileUploadParam2 param, HttpServletRequest request) {
        System.out.println(param.getRemark());
        FileUploadResult result = new FileUploadResult();
        // 获取上传的文件
        Collection<MultipartFile> uploadFiles = UploadUtil.getUploadFiles(request);
        for (MultipartFile multipartFile : uploadFiles) {
            FileUploadResult.FileMeta fileMeta = buildFileMeta(multipartFile);
            result.getFiles().add(fileMeta);
        }
        return result;
    }
    @ApiMapping(value = "demo.file.upload3")
    public FileUploadResult file3(FileUploadParam2 param, HttpServletRequest request) {
        System.out.println(param.getRemark());
        FileUploadResult result = new FileUploadResult();
        // 获取上传的文件
        Collection<MultipartFile> uploadFiles = UploadUtil.getUploadFiles(request);
        Optional<MultipartFile> first = uploadFiles.stream().findFirst();
        if (first.isPresent()) {
            MultipartFile multipartFile = first.get();
            try {
                String path = System.getProperty("user.dir");
                multipartFile.transferTo(new File(path + "/img_"+System.currentTimeMillis()+".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private FileUploadResult.FileMeta buildFileMeta(MultipartFile multipartFile) {
        // 文件名
        String fileName = multipartFile.getOriginalFilename();
        // 文件大小
        long size = multipartFile.getSize();
        // 文件内容
        String fileContent = null;
        try {
            fileContent = IOUtils.toString(multipartFile.getInputStream(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FileUploadResult.FileMeta(fileName, size, fileContent);
    }
}
