package com.gitee.sop.gatewaycommon.loadbalancer;

import com.gitee.sop.gatewaycommon.bean.ApiParamAware;
import com.gitee.sop.gatewaycommon.manager.EnvironmentKeys;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.StringUtils;

/**
 * @author tanghc
 */
public interface ServerChooserContext<T> extends ApiParamAware<T> {

    /**
     * 通过判断hostname来确定是否是预发布请求
     *
     * @param t t
     * @return 返回true：可以进入到预发环境
     */
    default boolean isRequestFromPreDomain(T t) {
        String domain = EnvironmentKeys.PRE_DOMAIN.getValue();
        if (StringUtils.isEmpty(domain)) {
            return false;
        }
        String[] domains = domain.split("\\,");
        return ArrayUtils.contains(domains, getHost(t));
    }

    default boolean isRequestGrayServer(T t) {
        ApiParam apiParam = getApiParam(t);
        return apiParam.fetchGrayRequest();
    }

    String getHost(T t);
}
