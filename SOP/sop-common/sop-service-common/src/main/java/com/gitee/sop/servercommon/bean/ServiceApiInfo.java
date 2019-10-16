package com.gitee.sop.servercommon.bean;

import com.gitee.sop.servercommon.route.RouteDefinition;
import lombok.Data;

import java.util.List;

/**
 * @author tanghc
 */
@Data
public class ServiceApiInfo {
    private String serviceId;
    private List<ApiMeta> apis;
    private List<RouteDefinition> routeDefinitionList;

    @Data
    public static class ApiMeta {
        /** 接口名 */
        private String name;
        /** 请求path */
        private String path;
        /** 版本号 */
        private String version;
        /** 是否忽略验证 */
        private int ignoreValidate;
        /** 是否合并结果 */
        private int mergeResult;
        /** 是否需要授权才能访问 */
        private int permission;
        /** 是否是原始Mapping */
        private boolean originalMapping;

        public ApiMeta() {
        }

        public ApiMeta(String name, String path, String version) {
            this.name = name;
            this.path = path;
            this.version = version;
        }

        public String fetchNameVersion() {
            return this.name + this.version;
        }
    }
}
