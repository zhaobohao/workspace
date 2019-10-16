package com.gitee.sop.gatewaycommon.param;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 存放上传文件
 * 
 * @author tanghc
 */
public class ApiUploadContext implements UploadContext {

    /**
     * key: 表单name
     */
    private Map<String, MultipartFile> fileMap;
    private List<MultipartFile> allFile;

    public ApiUploadContext(Map<String, MultipartFile> map) {
        if (map == null) {
            map = Collections.emptyMap();
        }
        this.fileMap = map;
        this.allFile = new ArrayList<>(map.values());
    }

    @Override
    public MultipartFile getFile(int index) {
        return this.allFile.get(index);
    }

    @Override
    public MultipartFile getFile(String name) {
        return fileMap.get(name);
    }

    @Override
    public List<MultipartFile> getAllFile() {
        return this.allFile;
    }
}
