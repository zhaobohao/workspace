package org.springbootdev.core.mp.base;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springbootdev.core.secure.SystemUser;
import org.springbootdev.core.secure.utils.SecureUtil;
import org.springbootdev.core.tool.constant.ToolConstant;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 填充器,自动写入各种共用的字段 ，节省代码量
 *
 * @author nieqiurong 2018-08-10 22:59:23.
 */
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {


	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("start insert fill ....");
		//避免使用metaObject.setValue()
		SystemUser user = SecureUtil.getUser();
		if (user != null) {
			this.strictInsertFill(metaObject, "createUser", Long.class, user.getUserId());
			this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
			this.strictInsertFill(metaObject, "updateUser", Long.class, user.getUserId());
			this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
			if(metaObject.hasGetter("status") && metaObject.getValue("status")==null)
				this.strictInsertFill(metaObject, "status", Integer.class, ToolConstant.DB_STATUS_NORMAL);
			if(metaObject.hasGetter("isDeleted") && metaObject.getValue("isDeleted")==null)
				this.strictInsertFill(metaObject, "isDeleted", Integer.class, ToolConstant.DB_NOT_DELETED);
			if (user != null && ToolConstant.ADMIN_TENANT_ID.equals(user.getTenantId())) {
				if(metaObject.hasGetter("tenantId") && metaObject.getValue("tenantId")==null)
					this.strictInsertFill(metaObject, "tenantId", String.class, user.getTenantId());
			}else if(user!=null){
				// 非管理租户添加数据，必须与操作者同一租户
				this.strictInsertFill(metaObject, "tenantId", String.class, user.getTenantId());
			}
			// 处理treeEntity的字段
			if(metaObject.hasGetter("isLeaf") && metaObject.getValue("isLeaf")==null){
				this.strictInsertFill(metaObject, "isLeaf", Integer.class, ToolConstant.DEFAULT_IS_LEAF);
			}
			if(metaObject.hasGetter("parentId") && metaObject.getValue("parentId")==null){
				this.strictInsertFill(metaObject, "parentId", Integer.class, ToolConstant.DEFAULT_TREE_ROOT_PARENT_ID);
			}
			if(metaObject.hasGetter("sort") && metaObject.getValue("sort")==null){
				this.strictInsertFill(metaObject, "sort", Integer.class, ToolConstant.DEFAULT_SORT);
			}
		}
	}
	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("start update fill ....");
		SystemUser user = SecureUtil.getUser();
		if (user != null) {
			this.strictUpdateFill(metaObject, "updateUser", Long.class, user.getUserId());
			this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
			if (user != null && ToolConstant.ADMIN_TENANT_ID.equals(user.getTenantId())) {
				if(metaObject.hasGetter("tenantId") && metaObject.getValue("tenantId")==null)
					this.strictUpdateFill(metaObject, "tenantId", String.class, user.getTenantId());
			}else if(user!=null){
				// 非管理租户添加数据，必须与操作者同一租户
				this.strictUpdateFill(metaObject, "tenantId", String.class, user.getTenantId());
			}
		}
	}
}
