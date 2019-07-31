package com.gitee.easyopen;

/**
 * 对象序列化
 * @author tanghc
 *
 */
public interface ResultSerializer {
    /**
     * 序列化
     * 
     * @param obj
     * @return 返回序列化后的结果
     */
    String serialize(Object obj);
}
