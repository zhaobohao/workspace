package com.gitee.easyopen;

/**
 * 负责对返回结果进行封装
 * 
 * @author tanghc
 *
 */
public class ApiResultCreator implements ResultCreator {

    @Override
    public Result createResult(Object returnObj) {
        return new ApiResult(returnObj);
    }

    @Override
    public Result createErrorResult(Object code, String errorMsg, Object data) {
        Result ret = new ApiResult();
        ret.setCode(code);
        ret.setMsg(errorMsg);
        ret.setData(data);
        return ret;
    }

}
