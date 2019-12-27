package org.springclouddev.core.boot.upload.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 用来处理文件上传后的业务逻辑
 */
public interface UploadFileHandler {

	void handler(MultipartFile upFile, Map<String,String> params) throws Exception;
}
