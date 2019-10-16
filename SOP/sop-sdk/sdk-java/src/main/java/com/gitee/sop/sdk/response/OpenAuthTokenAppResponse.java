package com.gitee.sop.sdk.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tanghc
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OpenAuthTokenAppResponse extends BaseResponse {
    /**
     * 授权令牌
     */
    private String app_auth_token;

    /**
     * 用户id
     */
    private String user_id;

    /**
     * 刷新令牌时使用。
     * 刷新令牌后，我们会保证老的app_auth_token从刷新开始10分钟内可继续使用，请及时替换为最新token
     */
    private String app_refresh_token;

    private String error;
    private String error_description;
}
