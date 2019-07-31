package com.gitee.apiconf.api;

import com.gitee.apiconf.api.param.AppParam;
import com.gitee.apiconf.api.param.AppRoleApiForm;
import com.gitee.apiconf.api.param.ClientParam;
import com.gitee.apiconf.api.param.RoleAddForm;
import com.gitee.apiconf.api.param.RoleParam;
import com.gitee.apiconf.api.param.RoleUpdateForm;
import com.gitee.apiconf.api.result.RoleVo;
import com.gitee.apiconf.common.MyMapperUtil;
import com.gitee.apiconf.common.TreeNode;
import com.gitee.apiconf.entity.PermApiInfo;
import com.gitee.apiconf.entity.PermRole;
import com.gitee.apiconf.entity.PermRolePermission;
import com.gitee.apiconf.entity.status.ApiInfoStatus;
import com.gitee.apiconf.mapper.PermApiInfoMapper;
import com.gitee.apiconf.mapper.PermRoleMapper;
import com.gitee.apiconf.mapper.PermRolePermissionMapper;
import com.gitee.apiconf.service.PermService;
import com.gitee.apiconf.service.SyncService;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.util.CopyUtil;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.Sort;
import com.gitee.fastmybatis.core.support.PageEasyui;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiService
public class PermissionApi {

    @Autowired
    PermRoleMapper permRoleMapper;
    @Autowired
    PermService permService;
    @Autowired
    PermApiInfoMapper permApiInfoMapper;
    @Autowired
    PermRolePermissionMapper permRolePermissionMapper;

    @Autowired
    SyncService syncService;

    /**
     * 获取所有的角色。树状结构
     *
     * @return
     */
    @Api(name = "role.tree.listall")
    List<TreeNode> roleTreeList() {
        String state = "open";
        TreeNode rootNode = new TreeNode();
        rootNode.setId(0L);
        rootNode.setState(state);
        rootNode.setText("角色");
        rootNode.setRoot(true);

        List<PermRole> allRoleList = permRoleMapper.list(new Query().orderby("gmt_create", Sort.ASC));
        List<TreeNode> children = new ArrayList<>();
        for (PermRole permRole : allRoleList) {
            TreeNode child = new TreeNode();
            child.setId(permRole.getRoleCode());
            child.setText(permRole.getDescription());
            child.setState(state);
            children.add(child);
        }
        rootNode.setChildren(children);
        return Arrays.asList(rootNode);
    }
    
    @Api(name = "role.listall")
    PageEasyui<?> roleListall() {
    	Query query = new Query();
    	PageEasyui<RoleVo> pageInfo = MyMapperUtil.queryToEasyui(this.permRoleMapper, query, RoleVo.class);
    	return pageInfo;
    }
    
    @Api(name = "role.add")
    void roleAdd(RoleAddForm param) {
    	PermRole role = new PermRole();
    	CopyUtil.copyPropertiesIgnoreNull(param, role);
    	permRoleMapper.save(role);
    }
    
    @Api(name = "role.update")
    void roleUpdate(RoleUpdateForm param) {
        PermRole role = permRoleMapper.getById(param.getId());
        CopyUtil.copyPropertiesIgnoreNull(param, role);
    	permRoleMapper.update(role);
    }

    /**
     * 查询所有的api，返回tree结构
     * @param param
     * @return
     */
    @Api(name = "app.api.tree.listall")
    List<TreeNode> apiTreeList(AppParam param) {
    	Query query = new Query();
    	query.eq("app", param.getApp())
                .eq("status", ApiInfoStatus.USING.getStatus())
                .orderby("order_index", Sort.ASC)
                .orderby("name", Sort.ASC)
                .orderby("version", Sort.ASC);
    	List<PermApiInfo> apiList = permApiInfoMapper.list(query);
        TreeNode rootNode = this.buildTreeData(apiList);
        return Arrays.asList(rootNode);
    }

    /** 构建树结构 */
    private TreeNode buildTreeData(List<PermApiInfo> apiList) {
        String stateOpen = "open";
        String stateClose = "closed";
        TreeNode rootNode = new TreeNode();
        rootNode.setId(0L);
        rootNode.setState(stateOpen);
        rootNode.setText("全部接口");
        rootNode.setRoot(true);

        int parentId = -100;
        Map<String, TreeNode> parentNodeMap = new HashMap<>();

        List<TreeNode> moduleChildren = new ArrayList<>();
        List<TreeNode> noParentChildren = new ArrayList<>();

        for (PermApiInfo apiInfo : apiList) {
            TreeNode child = new TreeNode();
            String moduleName = apiInfo.getModuleName();
            // 有模块名
            if (StringUtils.isNotBlank(moduleName)) {
                TreeNode parentNode = parentNodeMap.get(moduleName);
                if (parentNode == null) {
                    parentNode = new TreeNode();
                    parentNode.setId(parentId--);
                    parentNode.setText(moduleName);
                    parentNode.setState(stateClose);
                    parentNode.setChildren(new ArrayList<>());
                    parentNode.setOrderIndex(apiInfo.getOrderIndex());
                    parentNodeMap.put(moduleName, parentNode);
                    moduleChildren.add(parentNode);
                }
                List<TreeNode> parentNodeChildren = parentNode.getChildren();
                child.setId(apiInfo.getId());
                child.setText(apiInfo.getName() + appendVersion(apiInfo.getVersion()) + appendDescription(apiInfo.getDescription()));
                child.setState(stateOpen);
                parentNodeChildren.add(child);
            } else {
                child.setId(apiInfo.getId());
                child.setText(apiInfo.getName() + appendVersion(apiInfo.getVersion()) + appendDescription(apiInfo.getDescription()));
                child.setState(stateOpen);
                noParentChildren.add(child);
            }
        }
        Collections.sort(moduleChildren, new Comparator<TreeNode>() {
            @Override
            public int compare(TreeNode o1, TreeNode o2) {
                if (o1.getOrderIndex() == o2.getOrderIndex()) {
                    return o1.getText().compareTo(o2.getText());
                } else {
                    return Integer.compare(o1.getOrderIndex(), o2.getOrderIndex());
                }
            }
        });
        moduleChildren.addAll(noParentChildren);
        rootNode.setChildren(moduleChildren);
        return rootNode;
    }

    private static String appendVersion(String str) {
        if (StringUtils.isNotBlank(str)) {
            return "(" + str + ")";
        } else {
            return "";
        }
    }

    private static String appendDescription(String str) {
        if (StringUtils.isNotBlank(str)) {
            return "-" + str;
        } else {
            return "";
        }
    }

    @Api(name = "role.api.list")
    List<PermRolePermission> roleApiList(RoleParam param) {
    	Query query = Query.build(param);
    	query.join("inner join perm_api_info t2 on t.api_id=t2.id")
            .eq("t2.status", ApiInfoStatus.USING.getStatus());

    	List<PermRolePermission> rolePermList = permRolePermissionMapper.list(query);
        return rolePermList;
    }

    /**
     * 更新角色接口权限
     * @param param
     */
    @Api(name = "role.api.update")
    void roleApiUpdate(AppRoleApiForm param) {
        String app = param.getApp();
        String roleCode = param.getRoleCode();
        // 删除所有数据
        Query delQuery = new Query();
        delQuery.eq("app", app).eq("role_code", roleCode);
        permRolePermissionMapper.deleteByQuery(delQuery);

        List<Long> apiIds = param.getApiIds();
        if(CollectionUtils.isNotEmpty(apiIds)) {
            List<PermRolePermission> tobeSave = new ArrayList<>(apiIds.size());
            for (Long apiId : apiIds) {
                // apiId小于0的是模块id
                if (apiId < 0) {
                    continue;
                }
                PermRolePermission rec = new PermRolePermission();
                rec.setApiId(apiId);
                rec.setRoleCode(roleCode);
                rec.setApp(app);
                tobeSave.add(rec);
            }
            // 批量添加
            permRolePermissionMapper.saveBatch(tobeSave);
        }

        syncService.syncPermissionConfigByApp(param.getApp());
    }

    
    @Api(name = "client.rolecode.list")
    List<String> clientRoleCodeList(ClientParam param) {
        return permService.listClientRoleCode(param.getId());
    }

}
