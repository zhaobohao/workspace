package com.gitee.sop.gateway.manager;

import com.alibaba.fastjson.JSON;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.sop.gateway.entity.IsvInfo;
import com.gitee.sop.gateway.entity.PermIsvRole;
import com.gitee.sop.gateway.entity.PermRolePermission;
import com.gitee.sop.gateway.mapper.IsvInfoMapper;
import com.gitee.sop.gateway.mapper.PermIsvRoleMapper;
import com.gitee.sop.gateway.mapper.PermRolePermissionMapper;
import com.gitee.sop.gatewaycommon.bean.ChannelMsg;
import com.gitee.sop.gatewaycommon.bean.IsvRoutePermission;
import com.gitee.sop.gatewaycommon.manager.DefaultIsvRoutePermissionManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

/**
 * 从数据库中读取路由权限信息
 *
 * @author tanghc
 */
@Slf4j
@Service
public class DbIsvRoutePermissionManager extends DefaultIsvRoutePermissionManager {

    @Autowired
    Environment environment;

    @Autowired
    PermIsvRoleMapper permIsvRoleMapper;

    @Autowired
    PermRolePermissionMapper permRolePermissionMapper;

    @Autowired
    IsvInfoMapper isvInfoMapper;


    @Override
    public void load() {
        // key: appKey, value: roleCodeList
        Map<String, List<String>> appKeyRoleCodeMap = this.getIsvRoleCode();

        for (Map.Entry<String, List<String>> entry : appKeyRoleCodeMap.entrySet()) {
            this.loadIsvRoutePermission(entry.getKey(), entry.getValue());
        }
    }

    public void loadIsvRoutePermission(String appKey, List<String> roleCodeList) {
        Collections.sort(roleCodeList);
        List<String> routeIdList = this.getRouteIdList(roleCodeList);
        String roleCodeListMd5 = DigestUtils.md5Hex(JSON.toJSONString(routeIdList));
        IsvRoutePermission isvRoutePermission = new IsvRoutePermission();
        isvRoutePermission.setAppKey(appKey);
        isvRoutePermission.setRouteIdList(routeIdList);
        isvRoutePermission.setRouteIdListMd5(roleCodeListMd5);
        this.update(isvRoutePermission);
    }

    /**
     * 获取ISV对应的角色
     * @return 返回ISV角色信息，key：appId，value：角色code列表
     */
    public Map<String, List<String>> getIsvRoleCode() {
        Query query = new Query();
        List<PermIsvRole> permIsvRoles = permIsvRoleMapper.list(query);
        Map<String, List<String>> appKeyRoleCodeMap = permIsvRoles.stream()
                .map(permIsvRole -> {
                    IsvInfo isvInfo = isvInfoMapper.getById(permIsvRole.getIsvId());
                    if (isvInfo == null) {
                        return null;
                    }
                    IsvRole isvRole = new IsvRole();
                    isvRole.appKey = isvInfo.getAppKey();
                    isvRole.roleCode = permIsvRole.getRoleCode();
                    return isvRole;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(IsvRole::getAppKey,
                        mapping(IsvRole::getRoleCode, toList()))
                );
        return appKeyRoleCodeMap;
    }

    /**
     * 获取角色对应的路由
     *
     * @param roleCodeList
     * @return
     */
    public List<String> getRouteIdList(List<String> roleCodeList) {
        if (CollectionUtils.isEmpty(roleCodeList)) {
            return Collections.emptyList();
        }
        Query query = new Query();
        query.in("role_code", roleCodeList);
        List<PermRolePermission> rolePermissionList = permRolePermissionMapper.list(query);
        return rolePermissionList.stream()
                .map(PermRolePermission::getRouteId)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public void process(ChannelMsg channelMsg) {
        final IsvRoutePermission isvRoutePermission = channelMsg.toObject(IsvRoutePermission.class);
        switch (channelMsg.getOperation()) {
            case "reload":
                log.info("重新加载路由权限信息，isvRoutePermission:{}", isvRoutePermission);
                try {
                    load();
                } catch (Exception e) {
                    log.error("重新加载路由权限失败, channelMsg:{}", channelMsg, e);
                }
                break;
            case "update":
                log.info("更新ISV路由权限信息，isvRoutePermission:{}", isvRoutePermission);
                update(isvRoutePermission);
                break;
            case "remove":
                log.info("删除ISV路由权限信息，isvRoutePermission:{}", isvRoutePermission);
                remove(isvRoutePermission.getAppKey());
                break;
            default:

        }
    }


    @Data
    static class IsvRole {
        private String appKey;
        private String roleCode;
    }
}
