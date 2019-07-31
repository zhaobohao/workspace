package com.gitee.apiconf.service;

import com.gitee.apiconf.entity.PermClientRole;
import com.gitee.apiconf.mapper.PermApiInfoMapper;
import com.gitee.apiconf.mapper.PermClientRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermService {
    @Autowired
    PermClientRoleMapper permClientRoleMapper;
    @Autowired
    PermApiInfoMapper permApiInfoMapper;

    /**
     * 获取客户端角色码列表
     *
     * @param clientId
     * @return
     */
    public List<String> listClientRoleCode(Long clientId) {
        List<PermClientRole> list = permClientRoleMapper.listByColumn("client_id", clientId);
        List<String> retList = new ArrayList<>(list.size());
        for (PermClientRole permClientRole : list) {
            retList.add(permClientRole.getRoleCode());
        }
        return retList;
    }

}
