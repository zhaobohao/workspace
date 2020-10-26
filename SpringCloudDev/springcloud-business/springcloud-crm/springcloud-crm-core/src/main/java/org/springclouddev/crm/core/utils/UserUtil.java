package org.springclouddev.crm.core.utils;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.crm.core.common.Const;
import org.springclouddev.crm.core.entity.UserExtraInfo;
import org.springclouddev.crm.core.entity.UserInfo;
import org.springclouddev.crm.core.redis.Redis;

import javax.servlet.http.Cookie;
import java.util.Date;
import java.util.Objects;

/**
 * @author z
 * 用户操作相关方法
 */
@Slf4j
public class UserUtil {

    private static ThreadLocal<UserInfo> threadLocal = new TransmittableThreadLocal<>();


    public static UserInfo getUser() {
        return threadLocal.get();
    }

    public static Long getUserId() {
        if (threadLocal.get() == null) {
            return null;
        }
        return threadLocal.get().getUserId();
    }

    public static void setUser(UserInfo adminUser) {
        threadLocal.set(adminUser);
    }

    public static UserInfo setUser(Long userId) {
        UserInfo userInfo = UserCacheUtil.getUserInfo(userId);
        setUser(userInfo);
        return userInfo;
    }


    public static void removeUser() {
        threadLocal.remove();
    }

    /**
     * 验证签名是否正确
     *
     * @param key  key
     * @param salt 盐
     * @param sign 签名
     * @return 是否正确 true为正确
     */
    public static boolean verify(String key, String salt, String sign) {
        return sign.equals(sign(key, salt)) || sign.equals(signP(key, salt)) || sign.equals(signP2(key, salt));
    }

    /**
     * 签名数据
     *
     * @param key  key
     * @param salt 盐
     * @return 加密后的字符串
     */
    public static String sign(String key, String salt) {
        return SecureUtil.md5(key.concat("erp").concat(salt));
    }

    /**
     * 签名数据
     * PHP端签名
     *
     * @param key  key
     * @param salt 盐
     * @return 加密后的字符串
     */
    private static String signP(String key, String salt) {
        String username = key.substring(0, 11);
        String password = key.substring(11);
        return SecureUtil.md5(SecureUtil.md5(SecureUtil.sha1(username.concat(password))) + SecureUtil.md5(password.concat(salt)));
    }

    private static String signP2(String key, String salt) {
        String username = key.substring(0, 11);
        String password = key.substring(11);
        return SecureUtil.md5(SecureUtil.sha1(password) + SecureUtil.md5(password.concat(salt)));
    }

    public static void userExpire(String token) {
        Redis redis = BaseUtil.getRedis();
        if (redis.exists(token)) {
            Integer time = Const.MAX_USER_EXIST_TIME;
            redis.expire(token, time);
            redis.expire(Const.USER_ADMIN_TOKEN + getUserId(), time);
            redis.expire(Const.USER_MOBILE_TOKEN + getUserId(), time);
        }
    }

    public static void userToken(String token, UserInfo userInfo) {
        Redis redis = BaseUtil.getRedis();
        redis.setex(Const.USER_ADMIN_TOKEN + userInfo.getUserId(), Const.MAX_USER_EXIST_TIME, token);
        redis.setex(Const.USER_MOBILE_TOKEN + userInfo.getUserId(), Const.MAX_USER_EXIST_TIME, token);
        redis.setex(token, Const.MAX_USER_EXIST_TIME, userInfo);
        Cookie cookie = new Cookie(Const.TOKEN_NAME, token);
        cookie.setMaxAge(157680000);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        BaseUtil.getResponse().addCookie(cookie);
    }

    public static Long getSuperUser() {
        return 14773L;
    }

    public static Integer getSuperRole() {
        return 180162;
    }

    public static boolean isAdmin() {
        UserInfo userInfo = getUser();
        return userInfo.getUserId().equals(userInfo.getSuperUserId()) || userInfo.getRoles().contains(userInfo.getSuperRoleId());
    }

    public static void userExit(Long userId, Integer type) {
        userExit(userId, type, null);
    }

    public static void userExit(Long userId, Integer type, Integer extra) {
        Redis redis = BaseUtil.getRedis();
        String token = null, key = null;
        if (type == null || type == 1) {
            key = Const.USER_ADMIN_TOKEN + userId;
        }
        if (type == null || type == 2) {
            key = Const.USER_MOBILE_TOKEN + userId;
        }
        if (key != null) {
            if (redis.exists(key)) {
                token = redis.get(key);
                redis.del(key);
            }
        }
        //1代表被挤掉提示
        if (Objects.equals(1, extra) && token != null) {
            Long time = redis.ttl(token);
            if (time > 1L) {
                redis.setex(token, time.intValue(), new UserExtraInfo(1, new Date()));
            }
        } else {
            if (token != null) {
                redis.del(token);
            }
        }
    }

}
