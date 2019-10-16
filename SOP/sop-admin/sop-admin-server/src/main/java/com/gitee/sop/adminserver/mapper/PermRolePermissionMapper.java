package com.gitee.sop.adminserver.mapper;

import com.gitee.fastmybatis.core.mapper.CrudMapper;

import com.gitee.sop.adminserver.entity.PermRolePermission;
import com.gitee.sop.adminserver.entity.RouteRoleDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author tanghc
 */
public interface PermRolePermissionMapper extends CrudMapper<PermRolePermission, Long> {
    List<RouteRoleDTO> listRouteRole(@Param("routeIdList") List<String> routeIdList);
}
