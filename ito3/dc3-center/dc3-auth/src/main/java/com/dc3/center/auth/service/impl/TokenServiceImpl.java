package com.dc3.center.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.dc3.center.auth.bean.UserLimit;
import com.dc3.center.auth.service.TokenService;
import com.dc3.center.auth.service.UserService;
import com.dc3.common.constant.Common;
import com.dc3.common.exception.ServiceException;
import com.dc3.common.model.User;
import com.dc3.common.utils.Dc3Util;
import com.dc3.common.utils.KeyUtil;
import com.dc3.common.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 令牌服务接口实现类
 *
 * @author pnoker
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private UserService userService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public String generateSalt(String username) {
        String redisSaltKey = Common.Cache.USER + Common.Cache.SALT + Common.Cache.SEPARATOR + username;
        String salt = redisUtil.getKey(redisSaltKey);
        if (StringUtils.isBlank(salt)) {
            salt = RandomUtil.randomString(8);
            redisUtil.setKey(redisSaltKey, salt, Common.Cache.TOKEN_CACHE_TIMEOUT, TimeUnit.MINUTES);
        }
        return salt;
    }

    @Override
    public String generateToken(User user) {
        UserLimit userLimit = checkUserLimit(user.getName());
        Optional.ofNullable(userLimit).ifPresent(limit -> {
            if (limit.getTimes() > 3) {
                updateUserLimit(user.getName());
                throw new ServiceException("请求受限，请在 " + Dc3Util.formatData(limit.getExpireTime()) + " 之后重试");
            }
        });
        User select = userService.selectByName(user.getName());
        if (null != select) {
            String redisSaltKey = Common.Cache.USER + Common.Cache.SALT + Common.Cache.SEPARATOR + user.getName();
            String salt = redisUtil.getKey(redisSaltKey);
            if (StringUtils.isNotBlank(salt)) {
                if (Dc3Util.md5(select.getPassword() + salt).equals(user.getPassword())) {
                    String token = KeyUtil.generateToken(user.getName());
                    redisUtil.setKey(Common.Cache.USER + Common.Cache.TOKEN + Common.Cache.SEPARATOR + user.getName(), token, Common.Cache.TOKEN_CACHE_TIMEOUT, TimeUnit.HOURS);
                    return token;
                }
            }
        }
        updateUserLimit(user.getName());
        throw new ServiceException("用户名和密码不匹配");
    }

    @Override
    public boolean checkTokenValid(String username, String token) {
        String redisToken = redisUtil.getKey(Common.Cache.USER + Common.Cache.TOKEN + Common.Cache.SEPARATOR + username);
        if (StringUtils.isBlank(redisToken) || !redisToken.equals(token)) {
            return false;
        }
        try {
            KeyUtil.parserToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean cancelToken(String username) {
        redisUtil.removeKey(Common.Cache.USER + Common.Cache.TOKEN + Common.Cache.SEPARATOR + username);
        return true;
    }

    /**
     * 检测用户登录限制，返回该用户是否受限
     *
     * @param username
     * @return
     */
    private UserLimit checkUserLimit(String username) {
        String redisKey = Common.Cache.USER + Common.Cache.LIMIT + Common.Cache.SEPARATOR + username;
        return redisUtil.getKey(redisKey);
    }

    /**
     * 更新用户登录限制
     *
     * @param username
     * @return
     */
    private void updateUserLimit(String username) {
        int amount = 1;
        String redisKey = Common.Cache.USER + Common.Cache.LIMIT + Common.Cache.SEPARATOR + username;
        UserLimit limit = (UserLimit) Optional.ofNullable(redisUtil.getKey(redisKey)).orElse(new UserLimit(0, new Date()));
        limit.setTimes(limit.getTimes() + 1);
        if (limit.getTimes() > 3) {
            amount = (limit.getTimes() - 3) * Common.Cache.TOKEN_CACHE_TIMEOUT;
            limit.setExpireTime(Dc3Util.expireTime(amount, Calendar.MINUTE));
        }
        redisUtil.setKey(redisKey, limit, amount, TimeUnit.MINUTES);
    }
}
