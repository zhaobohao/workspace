package org.springclouddev.auth.granter;

import lombok.AllArgsConstructor;
import net.oschina.j2cache.Cache;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;
import org.springclouddev.auth.enums.CommonUserEnum;
import org.springclouddev.auth.utils.TokenUtil;
import org.springclouddev.common.cache.CacheNames;
import org.springclouddev.core.log.exception.ServiceException;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.utils.DigestUtil;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.core.tool.utils.StringUtil;
import org.springclouddev.core.tool.utils.WebUtil;
import org.springclouddev.system.user.entity.UserInfo;
import org.springclouddev.system.user.feign.IUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 验证码TokenGranter
 *
 * @author zhaobo
 */
@Component
@AllArgsConstructor
public class CaptchaTokenGranter  implements ITokenGranter {

    public static final String GRANT_TYPE = "captcha";

    private IUserClient userClient;
    @Autowired
    private CacheChannel cacheChannel;

    @Override
    public UserInfo grant(TokenParameter tokenParameter) {
        HttpServletRequest request = WebUtil.getRequest();

        String key = request.getHeader(TokenUtil.CAPTCHA_HEADER_KEY);
        String code = request.getHeader(TokenUtil.CAPTCHA_HEADER_CODE);
        // 获取验证码
        String redisCode = String.valueOf(cacheChannel.get(CacheNames.CAPTCHA_KEY, CacheNames.CAPTCHA_KEY+ key));
        // 判断验证码
        if (code == null || !StringUtil.equalsIgnoreCase(redisCode, code)) {
            throw new ServiceException(TokenUtil.CAPTCHA_NOT_CORRECT);
        }

        String tenantId = tokenParameter.getArgs().getStr("tenantId");
        String account = tokenParameter.getArgs().getStr("account");
        String password = tokenParameter.getArgs().getStr("password");
        UserInfo userInfo = null;
        if (Func.isNoneBlank(account, password)) {
            // 获取用户类型
            String userType = tokenParameter.getArgs().getStr("userType");
            R<UserInfo> result;
            // 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
            if (userType.equals(CommonUserEnum.WEB.getName())) {
                result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(password));
            } else if (userType.equals(CommonUserEnum.APP.getName())) {
                result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(password));
            } else {
                result = userClient.userInfo(tenantId, account, DigestUtil.encrypt(password));
            }
            userInfo = result.isSuccess() ? result.getData() : null;
        }
        return userInfo;
    }

}
