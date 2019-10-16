package com.gitee.sop.servercommon.bean;

import java.util.Date;

/**
 * 获取开放平台请求参数。
 * 可当做接口方法参数，泛型参数填业务参数类，如:
 * <pre>
 * <code>@ApiMapping(value = "alipay.goods.get")</code>
 * public Goods getGoods(OpenRequest&lt;GoodsParam&gt; openRequest){...}</code> <br/>
 * </pre>
 * 可通过<code>OpenRequest openRequest = ServiceContext.getCurrentContext().getOpenRequest();</code>方式进行获取
 * OpenContext
 * OpenParam
 *
 * @author tanghc
 */
public interface OpenContext<T> extends OpenBeanFactory {

    /**
     * 返回appid
     *
     * @return 返回appid
     */
    String getAppId();

    /**
     * 返回业务参数json字符串
     *
     * @return 返回字符串业务参数
     */
    String getBizContent();

    /**
     * 返回业务对象
     *
     * @return 业务对象
     */
    T getBizObject();

    /**
     * 返回字符编码
     *
     * @return 如UTF-8
     */
    String getCharset();

    /**
     * 返回接口名
     *
     * @return 如：alipay.goods.get
     */
    String getMethod();

    /**
     * 返回版本号
     *
     * @return 如：1.0
     */
    String getVersion();

    /**
     * 返回参数格式化
     *
     * @return 如：json
     */
    String getFormat();

    /**
     * 返回签名类型
     *
     * @return 如：RSA2
     */
    String getSignType();

    /**
     * 返回时间戳
     *
     * @return
     */
    Date getTimestamp();

    /**
     * 返回token，即access_token
     *
     * @return 返回token
     */
    String appAuthToken();

    /**
     * 返回回调地址
     *
     * @return 返回回调地址
     */
    String getNotifyUrl();

}
