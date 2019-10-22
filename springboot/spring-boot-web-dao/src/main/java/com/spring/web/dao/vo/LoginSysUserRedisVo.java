

package com.spring.web.dao.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *  登录用户redis对象，后台使用
 * </p>
 * @author zhaobohao
 * @date 2019-05-15
 **/
@Data
@ApiModel("系统用户登录值对象")
public class LoginSysUserRedisVo implements Serializable {

    private static final long serialVersionUID = -4497185071769175695L;

    /**
     * 登录对象vo
     */
    private LoginSysUserVo loginSysUserVo;

    /**
     * jwt token对象
     */
    private JwtTokenRedisVo jwtTokenRedisVo;

    /**
     * 唯一标识
     */
    private String uuid;

    /**
     * 登录ip
     */
    private ClientInfo clientInfo;

}
