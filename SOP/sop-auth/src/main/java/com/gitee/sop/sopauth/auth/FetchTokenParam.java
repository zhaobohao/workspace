package com.gitee.sop.sopauth.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 获取accessToken参数对象
 * @author tanghc
 */
@Data
public class FetchTokenParam {
    /**
     * 授权类型。<br>
     * 如果使用app_auth_code换取token，则为authorization_code，
     * 如果使用refresh_token换取新的token，则为refresh_token
     */
    @NotBlank(message = "授权类型不能为空")
    private String grant_type;

    /**
     * 授权码.与refresh_token二选一，用户对应用授权后得到，即第一步中开发者获取到的app_auth_code值
     */
    private String code;

    /**
     * 刷新令牌.与code二选一，可为空，刷新令牌时使用
     */
    private String refresh_token;
}
