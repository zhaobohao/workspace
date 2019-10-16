package com.gitee.sop.adminserver.api.service;

import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.Sort;
import com.gitee.fastmybatis.core.support.PageEasyui;
import com.gitee.fastmybatis.core.util.MapperUtil;
import com.gitee.sop.adminserver.api.isv.result.RoleVO;
import com.gitee.sop.adminserver.api.service.param.RouteAddParam;
import com.gitee.sop.adminserver.api.service.param.RouteDeleteParam;
import com.gitee.sop.adminserver.api.service.param.RoutePermissionParam;
import com.gitee.sop.adminserver.api.service.param.RouteSearchParam;
import com.gitee.sop.adminserver.api.service.param.RouteUpdateParam;
import com.gitee.sop.adminserver.api.service.result.RouteVO;
import com.gitee.sop.adminserver.bean.RouteConfigDto;
import com.gitee.sop.adminserver.common.BizException;
import com.gitee.sop.adminserver.entity.ConfigRouteBase;
import com.gitee.sop.adminserver.entity.ConfigServiceRoute;
import com.gitee.sop.adminserver.entity.PermRole;
import com.gitee.sop.adminserver.entity.PermRolePermission;
import com.gitee.sop.adminserver.entity.RouteRoleDTO;
import com.gitee.sop.adminserver.mapper.ConfigRouteBaseMapper;
import com.gitee.sop.adminserver.mapper.ConfigServiceRouteMapper;
import com.gitee.sop.adminserver.mapper.PermRoleMapper;
import com.gitee.sop.adminserver.mapper.PermRolePermissionMapper;
import com.gitee.sop.adminserver.service.RouteConfigService;
import com.gitee.sop.adminserver.service.RoutePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tanghc
 */
@ApiService
@ApiDoc("服务管理-路由管理")
@Slf4j
public class RouteApi {

    @Autowired
    PermRolePermissionMapper permRolePermissionMapper;

    @Autowired
    PermRoleMapper permRoleMapper;

    @Autowired
    ConfigRouteBaseMapper configRouteBaseMapper;

    @Autowired
    private ConfigServiceRouteMapper configServiceRouteMapper;

    @Autowired
    RoutePermissionService routePermissionService;

    @Autowired
    RouteConfigService routeConfigService;

    @ApiDocMethod(description = "路由列表，分页")
    @Api(name = "route.page")
    PageEasyui<RouteVO> page(RouteSearchParam form) {
        Query query = Query.build(form);
        query.orderby("id", Sort.ASC);
        PageEasyui<RouteVO> datagrid = MapperUtil.queryForEasyuiDatagrid(configServiceRouteMapper, query, RouteVO.class);
        List<String> routeIdList = datagrid.getRows()
                .parallelStream()
                .map(RouteVO::getId)
                .collect(Collectors.toList());

        if (routeIdList.isEmpty()) {
            return datagrid;
        }

        Map<String, Byte> routeIdStatusMap = configRouteBaseMapper
                .list(new Query().in("route_id", routeIdList))
                .stream()
                .collect(Collectors.toMap(ConfigRouteBase::getRouteId, ConfigRouteBase::getStatus));

        Map<String, List<RouteRoleDTO>> routeIdRoleMap = permRolePermissionMapper.listRouteRole(routeIdList)
                .parallelStream()
                .collect(Collectors.groupingBy(RouteRoleDTO::getRouteId));


        datagrid.getRows().forEach(vo -> {
            String routeId = vo.getId();
            List<RouteRoleDTO> routeRoleDTOS = routeIdRoleMap.getOrDefault(routeId, Collections.emptyList());
            vo.setRoles(routeRoleDTOS);
            Byte status = routeIdStatusMap.getOrDefault(routeId, vo.getStatus());
            vo.setStatus(status);
        });

        return datagrid;
    }

    @Api(name = "route.list", version = "1.2")
    @ApiDocMethod(description = "路由列表1.2")
    List<ConfigServiceRoute> listRoute2(RouteSearchParam param) {
        String serviceId = param.getServiceId();
        if (StringUtils.isBlank(serviceId)) {
            return Collections.emptyList();
        }
        Query query = Query.build(param);
        return configServiceRouteMapper.list(query);
    }

    @Api(name = "route.add")
    @ApiDocMethod(description = "新增路由")
    void addRoute(RouteAddParam param) {
        // TODO: 新增路由
    }

    @Api(name = "route.update")
    @ApiDocMethod(description = "修改路由")
    void updateRoute(RouteUpdateParam param) {
        this.updateRouteConfig(param);
    }

    @Api(name = "route.del")
    @ApiDocMethod(description = "删除路由")
    void delRoute(RouteDeleteParam param) {
        // TODO: 删除路由
    }


    private void updateRouteConfig(RouteUpdateParam routeUpdateParam) {
        String routeId = routeUpdateParam.getId();
        ConfigRouteBase configRouteBase = configRouteBaseMapper.getByColumn("route_id", routeId);
        boolean doSave = configRouteBase == null;
        if (doSave) {
            configRouteBase = new ConfigRouteBase();
            configRouteBase.setRouteId(routeId);
        }
        configRouteBase.setStatus(routeUpdateParam.getStatus().byteValue());

        int i = doSave ? configRouteBaseMapper.save(configRouteBase)
                : configRouteBaseMapper.update(configRouteBase);

        if (i > 0) {
            this.sendMsg(configRouteBase);
        }
    }

    private void sendMsg(ConfigRouteBase routeDefinition) {
        RouteConfigDto routeConfigDto = new RouteConfigDto();
        routeConfigDto.setRouteId(routeDefinition.getRouteId());
        routeConfigDto.setStatus(routeDefinition.getStatus().intValue());
        routeConfigService.sendRouteConfigMsg(routeConfigDto);
    }

    @Api(name = "route.role.get")
    @ApiDocMethod(description = "获取路由对应的角色", elementClass = RoleVO.class)
    List<RoleVO> getRouteRole(RouteSearchParam param) {
        if (StringUtils.isBlank(param.getId())) {
            throw new BizException("id不能为空");
        }
        return this.getRouteRole(param.getId());
    }

    /**
     * 获取路由对应的角色
     *
     * @param routeIdList routeIdList
     * @return
     */
    private List<RoleVO> getRouteRole(List<String> routeIdList) {
        // key:routeId, value: roleCode
        Map<String, List<String>> routeIdRoleCodeMap = permRolePermissionMapper.list(new Query().in("route_id", routeIdList))
                .stream()
                .collect(Collectors.groupingBy(PermRolePermission::getRouteId,
                        Collectors.mapping(PermRolePermission::getRoleCode, Collectors.toList())));


        return permRolePermissionMapper.list(new Query().in("route_id", routeIdList))
                .stream()
                .map(permRolePermission -> {
                    RoleVO vo = new RoleVO();
                    String roleCode = permRolePermission.getRoleCode();
                    PermRole permRole = permRoleMapper.getByColumn("role_code", roleCode);
                    BeanUtils.copyProperties(permRole, vo);
                    return vo;
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取路由对应的角色
     *
     * @param id routeId
     * @return
     */
    private List<RoleVO> getRouteRole(String id) {
        return permRolePermissionMapper.listByColumn("route_id", id)
                .stream()
                .map(permRolePermission -> {
                    RoleVO vo = new RoleVO();
                    String roleCode = permRolePermission.getRoleCode();
                    PermRole permRole = permRoleMapper.getByColumn("role_code", roleCode);
                    BeanUtils.copyProperties(permRole, vo);
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Api(name = "route.role.update")
    @ApiDocMethod(description = "更新路由对应的角色")
    public void updateRouteRole(RoutePermissionParam param) {
        RoutePermissionParam oldRoutePermission = this.buildOldRoutePermission(param.getRouteId());
        routePermissionService.updateRoutePermission(param);
        try {
            routePermissionService.sendRoutePermissionReloadMsg(oldRoutePermission);
        } catch (Exception e) {
            log.error("消息推送--路由权限(reload)失败", e);
            // 回滚
            routePermissionService.updateRoutePermission(oldRoutePermission);
            throw new BizException(e.getMessage());
        }
    }

    private RoutePermissionParam buildOldRoutePermission(String routeId) {
        List<RoleVO> routeRole = this.getRouteRole(routeId);
        List<String> roleCodeList = routeRole.stream()
                .map(RoleVO::getRoleCode)
                .collect(Collectors.toList());
        RoutePermissionParam routePermissionParam = new RoutePermissionParam();
        routePermissionParam.setRouteId(routeId);
        routePermissionParam.setRoleCode(roleCodeList);
        return routePermissionParam;
    }

}
