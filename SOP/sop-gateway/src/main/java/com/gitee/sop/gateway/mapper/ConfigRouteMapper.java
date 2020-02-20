package com.gitee.sop.gateway.mapper;

import com.gitee.sop.gatewaycommon.bean.RouteConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tanghc
 */
@Mapper
public interface ConfigRouteMapper {

    @Select("SELECT t.id AS routeId, t2.status " +
            "FROM config_service_route t " +
            "LEFT JOIN config_route_base t2 ON t.id=t2.route_id " +
            "WHERE t.service_id=#{serviceId}")
    List<RouteConfig> listRouteConfig(@Param("serviceId") String serviceId);

    @Select("SELECT t.id AS routeId, t2.status " +
            "FROM config_service_route t " +
            "LEFT JOIN config_route_base t2 ON t.id=t2.route_id ")
    List<RouteConfig> listAllRouteConfig();

}
