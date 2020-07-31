

package com.dc3.center.auth.service;

import com.dc3.common.base.Service;
import com.dc3.common.dto.UserDto;
import com.dc3.common.model.User;

/**
 * User Interface
 *
 * @author pnoker
 */
public interface UserService extends Service<User, UserDto> {

    /**
     * 根据用户名查询用户
     *
     * @param nama
     * @return User
     */
    User selectByName(String nama);

    /**
     * 根据用户名判断用户是否存在
     *
     * @param name
     * @return boolean
     */
    boolean checkUserValid(String name);

    /**
     * 重置密码
     *
     * @param id Id
     * @return boolean
     */
    boolean restPassword(Long id);
}
