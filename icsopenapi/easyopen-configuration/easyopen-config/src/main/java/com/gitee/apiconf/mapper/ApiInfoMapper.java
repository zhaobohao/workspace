package com.gitee.apiconf.mapper;

import com.gitee.easyopen.permission.ApiInfo;
import com.gitee.fastmybatis.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ApiInfoMapper extends Mapper<Void> {

    /**
     * 查询app下面所有appkey的权限
     * <pre>
     SELECT
     t.app_key,
     t4.name,
     t4.version
     FROM perm_client t
     INNER JOIN perm_client_role t2  ON t.id = t2.client_id
     INNER JOIN perm_role_permission t3  ON t2.role_code = t3.role_code AND t3.app = t.app
     INNER JOIN perm_api_info t4  ON t3.api_id = t4.id
     WHERE t.app = 'app1'
     * </pre>
     * @param app
     * @return
     */
    @Select("SELECT t.app_key, t4.name, t4.version " +
            "FROM perm_client t  " +
            "INNER JOIN perm_client_role t2 ON t.id = t2.client_id " +
            "INNER JOIN perm_role_permission t3 ON t2.role_code = t3.role_code AND t3.app = t.app " +
            "INNER JOIN perm_api_info t4 ON t3.api_id = t4.id " +
            "WHERE t.app = #{app}")
    @ResultType(ApiInfo.class)
    List<ApiInfo> listAppAuth(@Param("app") String app);
}
