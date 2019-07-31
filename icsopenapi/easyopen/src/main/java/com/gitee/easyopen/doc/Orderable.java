package com.gitee.easyopen.doc;

/**
 * @author tanghc
 */
public interface Orderable {
    /**
     * 获取排序字段名
     *
     * @return 排序字段名
     */
    String getOrderName();

    /**
     * 返回排序
     *
     * @return 排序
     */
    int getOrder();
}
