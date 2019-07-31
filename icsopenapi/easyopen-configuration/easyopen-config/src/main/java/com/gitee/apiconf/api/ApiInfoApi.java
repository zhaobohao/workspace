package com.gitee.apiconf.api;

import com.gitee.apiconf.api.param.ApiInfoPageParam;
import com.gitee.apiconf.api.param.AppApiRoleForm;
import com.gitee.apiconf.api.param.RolePermissionParam;
import com.gitee.apiconf.api.result.PermApiInfoVo;
import com.gitee.apiconf.common.MyMapperUtil;
import com.gitee.apiconf.entity.PermRolePermission;
import com.gitee.apiconf.mapper.PermApiInfoMapper;
import com.gitee.apiconf.mapper.PermRolePermissionMapper;
import com.gitee.apiconf.service.SyncService;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.Sort;
import com.gitee.fastmybatis.core.support.PageEasyui;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@ApiService
public class ApiInfoApi {

    @Autowired
    PermApiInfoMapper permApiInfoMapper;
    @Autowired
    PermRolePermissionMapper permRolePermissionMapper;

    @Autowired
    SyncService syncService;

    @Api(name = "app.api.pagelist")
    PageEasyui<PermApiInfoVo> appApiPagelist(ApiInfoPageParam param) {
        Query query = Query.build(param);
        query.orderby("gmt_create", Sort.DESC);
        PageEasyui<PermApiInfoVo> pageInfo = MyMapperUtil.queryToEasyui(permApiInfoMapper, query, PermApiInfoVo.class);
        List<PermApiInfoVo> list = pageInfo.getRows();
        for (PermApiInfoVo permApiInfoVo : list) {
            permApiInfoVo.setHasAuthed(this.hasAuthed(permApiInfoVo));
        }
        return pageInfo;
    }

    private boolean hasAuthed(PermApiInfoVo vo) {
        return permRolePermissionMapper.getByColumn("api_id", vo.getId()) != null;
    }

    /**
     * 查询接口对应的角色
     * @param param
     * @return
     */
    @Api(name = "api.role.listall")
    List<PermRolePermission> roleApiList(RolePermissionParam param) {
        Query query = Query.build(param);
        List<PermRolePermission> rolePermList = permRolePermissionMapper.list(query);
        return rolePermList;
    }

    /**
     * 更新api对应的角色
     *
     * @param param
     */
    @Api(name = "api.role.update")
    void roleApiUpdate(AppApiRoleForm param) {
        String app = param.getApp();
        Long apiId = param.getApiId();
        // 删除所有数据
        Query delQuery = new Query();
        delQuery.eq("app", app).eq("api_id", apiId);
        permRolePermissionMapper.deleteByQuery(delQuery);

        List<String> roleCodes = param.getRoleCodes();
        if (CollectionUtils.isNotEmpty(roleCodes)) {
            List<PermRolePermission> tobeSave = new ArrayList<>(roleCodes.size());
            for (String roleCode : roleCodes) {
                PermRolePermission apiRoleRec = new PermRolePermission();
                apiRoleRec.setApiId(apiId);
                apiRoleRec.setRoleCode(roleCode);
                apiRoleRec.setApp(app);
                tobeSave.add(apiRoleRec);
            }
            // 批量添加
            permRolePermissionMapper.saveBatch(tobeSave);
        }

        syncService.syncPermissionConfigByApp(app);
    }
}
