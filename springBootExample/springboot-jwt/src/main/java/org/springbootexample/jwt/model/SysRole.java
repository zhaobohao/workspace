package org.springbootexample.jwt.model;

import org.springbootexample.jwt.dao.domain.Permission;
import org.springbootexample.jwt.dao.domain.Role;

import java.io.Serializable;
import java.util.List;

/**
 * Description  : 角色信息
 */
public class SysRole extends Role implements Serializable {

    private static final long serialVersionUID = 1L;

    // 拥有的权限列表
    private List<Permission> permissions;

    public SysRole() {
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
