package org.springbootexample.pos.service;

import org.springbootexample.pos.dao.entity.ManagerInfo;
import org.springbootexample.pos.dao.repository.ManagerInfoDao;
import org.springbootexample.pos.exception.ForbiddenUserException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * 后台用户管理
 */

@Service
public class ManagerInfoService {

    @Resource
    private ManagerInfoDao managerInfoDao;

    /**
     * 通过名称查找用户
     * @param username
     * @return
     */
    public ManagerInfo findByUsername(String username) {
        ManagerInfo managerInfo =  managerInfoDao.findByUsername(username);
        if (managerInfo == null) {
            throw new UnknownAccountException();
        }
        if (managerInfo.getState() == 2) {
            throw new ForbiddenUserException();
        }
        if (managerInfo.getPidsList() == null) {
            managerInfo.setPidsList(Collections.singletonList(0));
        } else if (managerInfo.getPidsList().size() == 0) {
            managerInfo.getPidsList().add(0);
        }
        return managerInfo;
    }
}
