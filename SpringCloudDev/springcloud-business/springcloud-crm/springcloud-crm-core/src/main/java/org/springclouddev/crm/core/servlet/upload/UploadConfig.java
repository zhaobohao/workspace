package org.springclouddev.crm.core.servlet.upload;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 上传文件配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "crm.upload")
public class UploadConfig {
    /**
     * 上传类型 1 本地 2 alioss
     */
    private Integer config;

    /**
     * oss配置
     */
    private OssConfig oss;

    /**
     * 本地配置
     */
    private LocalConfig local;

    @Data
    public static class OssConfig {
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private Map<String,String> bucketName;
        private String publicUrl;

    }

    @Data
    public static class LocalConfig {
        private Map<String,String> uploadPath;
        private String publicUrl;
    }

}
