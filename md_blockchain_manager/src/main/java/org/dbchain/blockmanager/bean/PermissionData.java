package org.dbchain.blockmanager.bean;

import org.dbchain.blockmanager.model.Permission;

import java.util.List;

/**
 * @author zhaobo create on 2020/4/10.
 */
public class PermissionData extends BaseData {
    private List<Permission> permissions;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
