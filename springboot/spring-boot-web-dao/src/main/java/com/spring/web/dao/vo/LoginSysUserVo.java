

package com.spring.web.dao.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *  登录用户对象，响应给前端
 * </p>
 * @author zhaobohao
 * @date 2019-05-15
 **/
@Data
public class LoginSysUserVo implements Serializable {

    private static final long serialVersionUID = -1758338570596088158L;

    private Long id;

    private String userName;

}
