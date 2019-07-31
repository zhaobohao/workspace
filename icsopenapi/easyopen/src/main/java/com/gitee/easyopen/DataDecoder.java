package com.gitee.easyopen;

/**
 * 解码data部分参数。如果想要在执行方法前改变业务参数，使用此功能。
 * @author tanghc
 */
public interface DataDecoder {
    /**
     * 解码data部分参数
     * @param param api参数
     * @return 返回解码后的
     * @throws Exception
     */
    String decode(ApiParam param) throws Exception;
}
