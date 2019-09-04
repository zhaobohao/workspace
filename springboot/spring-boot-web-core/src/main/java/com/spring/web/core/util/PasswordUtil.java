

package com.spring.web.core.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 密码加密工具类
 * @author geekidea
 * @date 2018-11-08
 */
@Slf4j
public class PasswordUtil {
    private static final String KEY = "io.geekidea.springbootplus.springbootplus.pwd.key";
    public static String encrypt(String pwd){
        if (StrUtil.isBlank(pwd)){
            return null;
        }
        pwd = pwd + KEY;
        String newPwd = DigestUtil.sha256Hex(pwd) + DigestUtil.md5Hex(pwd);
        return newPwd;
    }

    public static void main(String[] args) {
        log.debug(encrypt("7c4a8d09ca3762af61e59520943dc26494f8941b"));
    }
}
