package com.gitee.easyopen;

/**
 * 生成结果
 * 
 * @author tanghc
 *
 */
public interface ResultCreator {

    /**
     * 生成结果
     * 
     * @param returnObj
     *            业务类返回的对象
     * @return 返回最终结果
     */
    Result createResult(Object returnObj);

    /**
     * 返回错误消息
     * 
     * @param code
     * @param errorMsg
     * @param data
     * @return 返回最终结果
     */
    Result createErrorResult(Object code, String errorMsg, Object data);

}
