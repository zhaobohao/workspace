package org.springbootexample.pos.dao.repository;

import org.springbootexample.pos.common.dao.repository.ManagerMapper;
import org.springbootexample.pos.dao.entity.ManagerInfo;

/**
 * Description  :
 */
public interface ManagerInfoDao extends ManagerMapper {
    ManagerInfo findByUsername(String username);
}
