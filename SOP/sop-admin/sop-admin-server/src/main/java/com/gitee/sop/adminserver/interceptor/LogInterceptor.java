package com.gitee.sop.adminserver.interceptor;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.ApiMeta;
import com.gitee.easyopen.interceptor.ApiInterceptorAdapter;
import com.gitee.easyopen.util.RequestUtil;

public class LogInterceptor extends ApiInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object serviceObj, Object argu)
            throws Exception {
        
        System.out.println("======preHandle======");
        System.out.println("IP:" + RequestUtil.getClientIP(request));
        System.out.println("接口类：" + serviceObj.getClass().getName());
        if(argu != null) {
            System.out.println("参数类：" + argu.getClass().getName());
        }
        
        Map<String, Claim> jwtData = ApiContext.getJwtData();
        if(jwtData != null) {
            System.out.println("-------jwt start-------");
            Set<Entry<String, Claim>> set = jwtData.entrySet();
            for (Entry<String, Claim> entry : set) {
                System.out.println("key:" + entry.getKey() + ", value:" + entry.getValue().asString());
            }
            System.out.println("-------jwt end-------");
        }
        
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object serviceObj, Object argu,
            Object result) throws Exception {
        System.out.println("======postHandle======");
        System.out.println("接口类：" + serviceObj.getClass().getName());
        if(argu != null) {
            System.out.println("参数类：" + argu.getClass().getName());
        }
        System.out.println("结果：" + JSON.toJSONString(result));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object serviceObj,
            Object argu, Object result, Exception e) throws Exception {
        System.out.println("======afterCompletion======");
        System.out.println("接口类：" + serviceObj.getClass().getName());
        if(argu != null) {
            System.out.println("参数类：" + argu.getClass().getName());
        }
        System.out.println("最终结果：" + JSON.toJSONString(result));
        System.out.println("e:" + e);
    }
    
}
