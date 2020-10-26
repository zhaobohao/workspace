package org.springclouddev.crm.admin.mapper;

import org.springclouddev.crm.admin.api.bo.DeptVO;
import org.springclouddev.crm.admin.api.entity.AdminDept;
import org.springclouddev.crm.core.servlet.BaseMapper;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
public interface AdminDeptMapper extends BaseMapper<AdminDept> {

    List<DeptVO> queryDeptUserList();
}
