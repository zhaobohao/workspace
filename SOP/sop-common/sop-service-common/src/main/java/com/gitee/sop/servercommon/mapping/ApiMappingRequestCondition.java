package com.gitee.sop.servercommon.mapping;

import com.gitee.sop.servercommon.bean.ParamNames;
import com.gitee.sop.servercommon.bean.ServiceConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tanghc
 */
@Getter
@Slf4j
public class ApiMappingRequestCondition implements RequestCondition<ApiMappingRequestCondition> {

    private String defaultVersion = ServiceConfig.getInstance().getDefaultVersion();

    private ApiMappingInfo apiMappingInfo;

    public ApiMappingRequestCondition(ApiMappingInfo apiMappingInfo) {
        this.apiMappingInfo = apiMappingInfo;
    }

    @Override
    public ApiMappingRequestCondition combine(ApiMappingRequestCondition other) {
        return this;
    }

    /**
     * 如果版本号跟当前对象中的版本号匹配，则表是命中的对应的方法
     * 否则返回null，表示不匹配
     *
     * @param request
     * @return 返回ApiMappingRequestCondition
     */
    @Override
    public ApiMappingRequestCondition getMatchingCondition(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String path = apiMappingInfo.getName() + "/" + apiMappingInfo.getVersion() + "/";
        if (servletPath.contains(path)) {
            return this;
        }
        String version = this.getVersion(request);
        if (version.equals(this.apiMappingInfo.getVersion())) {
            return this;
        }
        return null;
    }

    protected String getVersion(HttpServletRequest request) {
        String version = request.getHeader(ParamNames.HEADER_VERSION_NAME);
        if (version == null) {
            version = request.getParameter(ParamNames.VERSION_NAME);
        }
        return version == null ? defaultVersion : version;
    }


    /**
     * 对两个RequestCondition对象进行比较，这里主要是如果存在两个注册的一样的Mapping，
     * 那么就会对这两个Mapping进行排序，以判断哪个Mapping更适合处理当前request请求
     *
     * @param other
     * @param request
     * @return 返回-1,0,1
     */
    @Override
    public int compareTo(ApiMappingRequestCondition other, HttpServletRequest request) {
        return this.apiMappingInfo.getVersion().compareTo(other.apiMappingInfo.getVersion());
    }

}
