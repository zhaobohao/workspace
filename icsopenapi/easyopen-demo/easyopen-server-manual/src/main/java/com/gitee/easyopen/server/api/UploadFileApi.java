package com.gitee.easyopen.server.api;

import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.ApiResult;
import com.gitee.easyopen.UploadContext;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.easyopen.server.api.param.GoodsParam;
import com.gitee.easyopen.server.api.param.UploadParam;
import com.gitee.easyopen.server.api.param.UploadParam2;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@ApiService
@ApiDoc("文件上传")
public class UploadFileApi {

    /**
     * 方式1
     * @param param
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @Api(name = "file.upload")
    @ApiDocMethod(description = "文件上传")
    Object upload(UploadParam param) throws IllegalStateException, IOException {
        // 获取上传文件
        MultipartFile headImgFile = param.getHeadImg();
        MultipartFile idcardImgFile = param.getIdcardImg();

        StringBuilder sb = new StringBuilder();
        sb.append("表单名：").append(headImgFile.getName()).append(",")
        .append("文件大小：").append(headImgFile.getSize()).append(";");
        
        sb.append("表单名：").append(idcardImgFile.getName()).append(",")
        .append("文件大小：").append(idcardImgFile.getSize()).append(";");
        
        // headImgFile.getInputStream(); // 返回文件流
        // headImgFile.getBytes(); // 返回文件数据流
        
        headImgFile.transferTo(new File("D:/new_" + headImgFile.getOriginalFilename()));
        idcardImgFile.transferTo(new File("D:/new_" + idcardImgFile.getOriginalFilename()));

        return new ApiResult(sb.toString());
    }
    
    /**
     * 方式2
     * 
     * @param param
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @Api(name = "file.upload2")
    Object upload2(GoodsParam param) throws IllegalStateException, IOException {
        // 获取上传文件上下文
        UploadContext uploadContext = ApiContext.getUploadContext();
        // 返回所有的上传文件
        List<MultipartFile> files = uploadContext.getAllFile();
        
        StringBuilder sb = new StringBuilder();
        
        for (MultipartFile multipartFile : files) {
            sb
            .append("表单名：").append(multipartFile.getName()).append(",")
            .append("文件大小：").append(multipartFile.getSize()).append(";")
            ;
            // multipartFile.getInputStream(); // 返回文件流
            // multipartFile.getBytes(); // 返回文件数据流
            // 写文件到本地
            multipartFile.transferTo(new File("D:/new2_" + multipartFile.getOriginalFilename()));
        }
        
        return new ApiResult(sb.toString());
    }
    
    @Api(name = "file.upload3")
    @ApiDocMethod(description = "文件上传,不确定数量")
    Object upload(UploadParam2 param) throws IllegalStateException, IOException {
        // 获取上传文件
        List<MultipartFile> files = param.getFiles();
        
        StringBuilder sb = new StringBuilder();
        
        for (MultipartFile multipartFile : files) {
            sb
            .append("表单名：").append(multipartFile.getName()).append(",")
            .append("文件名：").append(multipartFile.getOriginalFilename()).append(",")
            .append("文件大小：").append(multipartFile.getSize()).append(";")
            ;
            
            multipartFile.transferTo(new File("D:/new2_" + multipartFile.getOriginalFilename()));
            
        }
        
        return new ApiResult(sb.toString());
    }
}
