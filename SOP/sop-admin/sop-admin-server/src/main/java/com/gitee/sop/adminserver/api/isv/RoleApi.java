package com.gitee.sop.adminserver.api.isv;

import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.easyopen.util.CopyUtil;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.Sort;
import com.gitee.fastmybatis.core.support.PageEasyui;
import com.gitee.fastmybatis.core.util.MapperUtil;
import com.gitee.sop.adminserver.api.isv.param.RoleForm;
import com.gitee.sop.adminserver.api.isv.param.RolePageParam;
import com.gitee.sop.adminserver.api.isv.result.RoleVO;
import com.gitee.sop.adminserver.common.BizException;
import com.gitee.sop.adminserver.entity.PermRole;
import com.gitee.sop.adminserver.entity.PermRolePermission;
import com.gitee.sop.adminserver.mapper.PermRoleMapper;
import com.gitee.sop.adminserver.mapper.PermRolePermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tanghc
 */
@ApiService
@ApiDoc("ISV管理")
@Slf4j
public class RoleApi {

    @Autowired
    PermRoleMapper permRoleMapper;

    @Autowired
    PermRolePermissionMapper permRolePermissionMapper;

    @Api(name = "role.listall")
    List<RoleVO> roleListall() {
        Query query = new Query();
        query.orderby("id", Sort.ASC);
        return permRoleMapper.list(query).stream()
                .map(permRole -> {
                    RoleVO vo = new RoleVO();
                    CopyUtil.copyProperties(permRole, vo);
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @ApiDocMethod(description = "获取角色，分页")
    @Api(name = "role.page")
    PageEasyui<RoleVO> pageRole(RolePageParam rolePage) {
        Query query = Query.build(rolePage);
        return MapperUtil.queryForEasyuiDatagrid(permRoleMapper, query, RoleVO.class);
    }

    @Api(name = "role.add")
    void addRole(RoleForm roleForm) {
        PermRole rec = permRoleMapper.getByColumn("role_code", roleForm.getRoleCode());
        if (rec != null) {
            throw new BizException("角色码已存在");
        }
        PermRole permRole = new PermRole();
        CopyUtil.copyPropertiesIgnoreNull(roleForm, permRole);
        permRoleMapper.saveIgnoreNull(permRole);
    }

    @Api(name = "role.update")
    void updateRole(RoleForm roleForm) {
        PermRole rec = permRoleMapper.getById(roleForm.getId());
        rec.setDescription(roleForm.getDescription());
        permRoleMapper.updateIgnoreNull(rec);
    }

    @Api(name = "role.del")
    void delRole(long id) {
        PermRole rec = permRoleMapper.getById(id);
        PermRolePermission rolePermission = permRolePermissionMapper.getByColumn("role_code", rec.getRoleCode());
        if (rolePermission != null) {
            throw new BizException("该角色已使用，无法删除");
        }
        permRoleMapper.deleteById(id);
    }
}
