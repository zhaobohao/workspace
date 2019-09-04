

package com.spring.web.core.util;

import cn.hutool.core.util.StrUtil;
import com.spring.web.core.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;


/**
 * @author zhaobohao
 * @date 2018-11-08
 */
@Slf4j
public class LoginUtil {

    /**
     * TOKEN有效时间，单位分钟
     */
    public static Integer TOKEN_VALID_TIME_MINUTE;

    static {
        Environment environment = SpringContextUtil.getBean(Environment.class);
        String time = environment.getProperty("spring-web-demo.login.token-valid-time");
        TOKEN_VALID_TIME_MINUTE = Integer.valueOf(time);
        log.info("TOKEN_VALID_TIME_MINUTE:{}",TOKEN_VALID_TIME_MINUTE);
    }

    /**
     * 从请求头或者请求参数中
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request){
        // 从请求头中获取token
        String token = request.getHeader(CommonConstant.TOKEN);
        if (StrUtil.isBlank(token)){
            // 从请求参数中获取token
            token = request.getParameter(CommonConstant.TOKEN);
        }
        return token;
    }

//    /**
//     * 获取当前登陆用户对象
//     * @return
//     */
//    public static LoginSysUserVo getLoginSysUser(){
//        LoginSysUserVo loginSysUserVo = (LoginSysUserVo) HttpServletRequestUtil.getRequest().getAttribute(CommonConstant.LOGIN_SYS_USER);
//        return loginSysUserVo;
//    }
//
//    /**
//     * 获取当前登陆用户的ID
//     * @return
//     */
//    public static String getUserId(){
//        LoginSysUserVo loginSysUserVo = getLoginSysUser();
//        if (loginSysUserVo !=null){
//            return loginSysUserVo.getUserId();
//        }
//        return null;
//    }
//
//    /**
//     * 获取当前登陆用户的账号
//     * @return
//     */
//    public static String getAccount(){
//        LoginSysUserVo loginSysUserVo = getLoginSysUser();
//        if (loginSysUserVo !=null){
//            return loginSysUserVo.getAccount();
//        }
//        return null;
//    }
//
//    /**
//     * 获取当前登陆用户的名称
//     * @return
//     */
//    public static String getUserName(){
//        LoginSysUserVo loginSysUserVo = getLoginSysUser();
//        if (loginSysUserVo !=null){
//            return loginSysUserVo.getUserName();
//        }
//        return null;
//    }
//
//    /**
//     * 判断是否是管理员，true:是,false:否
//     * @return
//     */
//    public static Boolean isAdmin() {
//        LoginSysUserVo loginSysUserVo = getLoginSysUser();
//        if (loginSysUserVo !=null){
//            return loginSysUserVo.getAdmin();
//        }
//        return null;
//    }
}
