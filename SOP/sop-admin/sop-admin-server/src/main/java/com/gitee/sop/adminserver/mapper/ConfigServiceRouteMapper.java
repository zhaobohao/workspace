package com.gitee.sop.adminserver.mapper;

import com.gitee.fastmybatis.core.mapper.CrudMapper;
import com.gitee.sop.adminserver.entity.ConfigServiceRoute;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author tanghc
 */
public interface ConfigServiceRouteMapper extends CrudMapper<ConfigServiceRoute, Long> {

    @Select("SELECT distinct service_id FROM config_service_route")
    List<String> listAllServiceId();

}
