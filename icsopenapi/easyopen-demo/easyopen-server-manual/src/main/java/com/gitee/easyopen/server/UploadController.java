package com.gitee.easyopen.server;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.gitee.easyopen.ApiResult;

@Controller
public class UploadController {

    @GetMapping("goUpload")
    public String goUpload() {
        return "upload";
    }
    
    @PostMapping("fileUpload")
    @ResponseBody
    public Object upload(HttpServletRequest request) throws IllegalStateException, IOException {
        // 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            // 将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            Map<String, MultipartFile> fileMap = multiRequest.getFileMap();
            if (fileMap != null) {
                System.out.println(fileMap.keySet());
                Collection<MultipartFile> files = fileMap.values();
                for (MultipartFile file : files) {
                    String fileName = file.getOriginalFilename();
                    System.out.println(fileName);
                }
            }

        }

        System.out.println(request.getParameter("body_data"));

        return new ApiResult();
    }

}
