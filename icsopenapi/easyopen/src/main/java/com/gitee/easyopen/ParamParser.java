package com.gitee.easyopen;

import javax.servlet.http.HttpServletRequest;

/**
 * 负责解析参数
 * @author tanghc
 *
 */
public interface ParamParser {

    /**
     * 从request提取参数
     * @param request
     * @return 返回ApiParam
     * @throws Exception 
     */
    ApiParam parse(HttpServletRequest request);
}
