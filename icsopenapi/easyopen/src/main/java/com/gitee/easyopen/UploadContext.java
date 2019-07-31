package com.gitee.easyopen;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 获取上传文件
 * 
 * @author tanghc
 */
public interface UploadContext {
    /**
     * 根据索引获取上传文件,从0开始
     * 
     * @param index
     * @return 返回上传文件信息
     */
    MultipartFile getFile(int index);

    /**
     * 根据表单名获取上传文件
     * 
     * @param name
     *            表单名称
     * @return 返回上传文件信息
     */
    MultipartFile getFile(String name);

    /**
     * 获取所有的上传文件
     * 
     * @return 返回所有的上传文件
     */
    List<MultipartFile> getAllFile();
}
