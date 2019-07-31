package com.gitee.easyopen;

import java.io.Serializable;

/**
 * @author tanghc
 */
public interface Param extends Serializable {

    /**
     * 获取接口名
     * @return 返回接口名
     */
    String fatchName();

    /**
     * 获取版本号
     * @return 返回版本号
     */
    String fatchVersion();

    /**
     * 获取appKey
     * @return 返回appKey
     */
    String fatchAppKey();

    /**
     * 获取业务参数
     * @return 返回业务参数
     */
    String fatchData();

    /**
     * 获取时间戳
     * @return 返回时间戳
     */
    String fatchTimestamp();

    /**
     * 获取签名串
     * @return 返回签名串
     */
    String fatchSign();

    /**
     * 获取格式化类型
     * @return 返回格式化类型
     */
    String fatchFormat();

    /**
     * 获取accessToken
     * @return 返回accessToken
     */
    String fatchAccessToken();

    /**
     * 获取签名方式
     * @return 返回签名方式
     */
    String fatchSignMethod();

}