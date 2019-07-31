package com.gitee.easyopen.support;

import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.ApiParam;
import com.gitee.easyopen.exception.ApiException;
import com.gitee.easyopen.interceptor.ApiInterceptorAdapter;
import com.gitee.easyopen.message.Errors;
import com.gitee.easyopen.permission.PermissionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限验证拦截器
 * @author tanghc
 */
public class PermissionInterceptor extends ApiInterceptorAdapter {

    private ApiException exception = new ApiException(Errors.NO_PERMISSION.getMsg(), Errors.NO_PERMISSION.getCode());


    public PermissionInterceptor() {}

    /**
     * @param code 禁止访问错误码
     * @param msg 禁止访问信息
     */
    public PermissionInterceptor(String code,String msg) {
        this.exception = new ApiException(msg, code);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object serviceObj, Object argu) throws Exception {
        PermissionManager permissionManager = ApiContext.getApiConfig().getPermissionManager();
        if (permissionManager == null) {
            logger.warn("权限PermissionManager为null，请检查设置！");
            return true;
        }
        ApiParam param = ApiContext.getApiParam();
        boolean canVisit = permissionManager.canVisit(param.fatchAppKey(), param.fatchName(), param.fatchVersion());
        if(!canVisit) {
            throw exception;
        }
        return canVisit;
    }
}
