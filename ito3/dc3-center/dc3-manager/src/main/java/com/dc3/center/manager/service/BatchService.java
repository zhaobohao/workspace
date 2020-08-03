package com.dc3.center.manager.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>BatchService Interface
 *
 * @author pnoker
 */
public interface BatchService {

    /**
     * 批量导入 All, 包含：驱动->模版->驱动配置->位号->设备->位号配置
     *
     * @param multipartFile MultipartFile
     * @return
     */
    Boolean batchImport(MultipartFile multipartFile);

}
