
package org.springclouddev.system.feign;

import org.springclouddev.core.tool.api.R;
import org.springclouddev.system.entity.Dept;
import org.springclouddev.system.entity.Role;
import org.springclouddev.system.entity.Tenant;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 * @author zhaobohao
 */
@Component
public class ISysClientFallback implements ISysClient {
	@Override
	public String getDeptName(Long id) {
		return null;
	}

	@Override
	public Dept getDept(Long id) {
		return null;
	}

	@Override
	public String getRoleName(Long id) {
		return null;
	}

	@Override
	public String getRoleAlias(Long id) {
		return null;
	}

	@Override
	public List<String> getRoleNames(String roleIds) {
		return null;
	}

	@Override
	public Role getRole(Long id) {
		return null;
	}

	@Override
	public List<String> getPermission(String id) {
		return null;
	}

	@Override
	public R<Tenant> getTenant(Long id) {
		return null;
	}

	@Override
	public R<Tenant> getTenant(String tenantId) {
		return null;
	}

	@Override
	public String getDeptIds(String tenantId, String deptNames) {
		return null;
	}

	@Override
	public List<String> getDeptNames(String deptIds) {
		return null;
	}

	@Override
	public String getPostIds(String tenantId, String postNames) {
		return null;
	}

	@Override
	public List<String> getPostNames(String postIds) {
		return null;
	}

	@Override
	public String getRoleIds(String tenantId, String roleNames) {
		return null;
	}
}
