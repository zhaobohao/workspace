package com.gitee.sop.servercommon.bean;

import com.gitee.sop.servercommon.route.RouteDefinition;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * @author tanghc
 */
@Data
public class ServiceApiInfo {
    private String serviceId;
    private List<ApiMeta> apis;
    private List<RouteDefinition> routeDefinitionList;

    @Getter
    @Setter
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
        /** 是否需要token */
        private int needToken;
        /** 是否是兼容模式，即使用了@ApiAbility注解 */
        private int compatibleMode;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ApiMeta apiMeta = (ApiMeta) o;
            return name.equals(apiMeta.name) &&
                    Objects.equals(version, apiMeta.version);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, version);
        }
    }
}
