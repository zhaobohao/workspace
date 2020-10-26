package org.springclouddev.crm.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springclouddev.crm.admin.api.bo.AdminDeptBO;
import org.springclouddev.crm.admin.api.bo.AdminDeptQueryBO;
import org.springclouddev.crm.admin.api.bo.DeptVO;
import org.springclouddev.crm.admin.api.entity.AdminDept;
import org.springclouddev.crm.admin.api.entity.SimpleDept;
import org.springclouddev.crm.admin.api.vo.AdminDeptVO;
import org.springclouddev.crm.admin.common.AdminCodeEnum;
import org.springclouddev.crm.admin.mapper.AdminDeptMapper;
import org.springclouddev.crm.admin.service.IAdminDeptService;
import org.springclouddev.crm.admin.service.IAdminUserService;
import org.springclouddev.crm.core.common.BaseStatusEnum;
import org.springclouddev.crm.core.common.SystemCodeEnum;
import org.springclouddev.crm.core.exception.CrmException;
import org.springclouddev.crm.core.servlet.BaseServiceImpl;
import org.springclouddev.crm.core.utils.RecursionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
@Service
public class AdminDeptServiceImpl extends BaseServiceImpl<AdminDeptMapper, AdminDept> implements IAdminDeptService {

    @Autowired
    private IAdminUserService adminUserService;

    /**
     * 查询部门树
     *
     * @param queryBO 上级ID
     * @return data
     */
    @Override
    public List<AdminDeptVO> queryDeptTree(AdminDeptQueryBO queryBO) {
        if (queryBO.getId() == null) {
            queryBO.setId(0);
        }
        List<AdminDept> adminDeptList = list();
        if ("tree".equals(queryBO.getType())) {
            return RecursionUtil.getChildListTree(adminDeptList, "pid", queryBO.getId(), "deptId", "children", AdminDeptVO.class);
        }
        if ("update".equals(queryBO.getType())) {
            List<Integer> ids = RecursionUtil.getChildList(adminDeptList, "pid", queryBO.getId(), "deptId", "deptId");
            adminDeptList.removeIf(dept -> ids.contains(dept.getDeptId()));
        }
        return adminDeptList.stream().map(obj -> {
            AdminDeptVO r = new AdminDeptVO();
            BeanUtil.copyProperties(obj, r);
            return r;
        }).collect(Collectors.toList());
    }

    /**
     * 查询部门下属部门
     *
     * @param parentId 上级ID
     * @return data
     */
    @Override
    public List<Integer> queryChildDept(Integer parentId) {
        return RecursionUtil.getChildList(list(), "pid", parentId, "deptId", "deptId");
    }

    /**
     * 新增部门
     *
     * @param adminDeptBO 部门信息
     */
    @Override
    public void addDept(AdminDeptBO adminDeptBO) {
        //pid为0的部门只能存在一个
        if (BaseStatusEnum.CLOSE.getStatus().equals(adminDeptBO.getPid())) {
            throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
        }
        AdminDept adminDept=new AdminDept();
        BeanUtil.copyProperties(adminDeptBO, adminDept) ;
        save(adminDept);
    }

    /**
     * 修改部门
     *
     * @param adminDeptBO 部门信息
     */
    @Override
    public void setDept(AdminDeptBO adminDeptBO) {
        List<Integer> deptIdList = queryChildDept(adminDeptBO.getDeptId());
        deptIdList.add(adminDeptBO.getDeptId());
        if (deptIdList.contains(adminDeptBO.getPid())) {
            throw new CrmException(AdminCodeEnum.ADMIN_PARENT_DEPT_ERROR);
        }
        AdminDept adminDept = getById(adminDeptBO.getDeptId());
        //pid为0的部门只能存在一个
        if ((!adminDept.getPid().equals(0)) && adminDeptBO.getPid().equals(0)) {
            throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
        }
        adminDept.setName(adminDeptBO.getName());
        adminDept.setPid(adminDeptBO.getPid());
        updateById(adminDept);
    }

    /**
     * 删除部门
     *
     * @param deptId 部门ID
     */
    @Override
    public void deleteDept(Integer deptId) {
        Integer userCount = adminUserService.query().eq("dept_id", deptId).count();
        if (userCount > 0) {
            throw new CrmException(AdminCodeEnum.ADMIN_DEPT_REMOVE_EXIST_USER_ERROR);
        }
        Integer deptCount = query().eq("pid", deptId).count();
        if (deptCount > 0) {
            throw new CrmException(AdminCodeEnum.ADMIN_DEPT_REMOVE_EXIST_DEPT_ERROR);
        }
        removeById(deptId);
    }

    /**
     * 根据部门ID获取部门名称
     *
     * @param deptId 部门ID
     * @return data
     */
    @Override
    public String getNameByDeptId(Integer deptId) {
        AdminDept adminDept = query().select("name").eq("dept_id", deptId).one();
        if (adminDept == null) {
            return "";
        }
        return adminDept.getName();
    }

    /**
     * 根据ids查询用户信息
     *
     * @param ids id列表
     * @return data
     */
    @Override
    public List<SimpleDept> queryDeptByIds(List<Integer> ids) {
        if (ids.size() == 0) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<AdminDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(AdminDept::getDeptId, AdminDept::getName).in(AdminDept::getDeptId, ids);
        return list(queryWrapper).stream().map(obj -> {
            SimpleDept simpleDept = new SimpleDept();
            simpleDept.setId(obj.getDeptId());
            simpleDept.setName(obj.getName());
            return simpleDept;
        }).collect(Collectors.toList());
    }

    @Override
    public List<DeptVO> queryDeptUserList() {
        return getBaseMapper().queryDeptUserList();
    }
}
