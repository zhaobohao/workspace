package org.springclouddev.crm.admin.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import org.springclouddev.crm.admin.api.bo.AdminUserBO;
import org.springclouddev.crm.admin.api.bo.UserBookBO;
import org.springclouddev.crm.admin.api.entity.AdminUser;
import org.springclouddev.crm.admin.api.vo.AdminUserVO;
import org.springclouddev.crm.admin.api.vo.HrmSimpleUserVO;
import org.springclouddev.crm.admin.api.vo.UserBookVO;
import org.springclouddev.crm.core.entity.BasePage;
import org.springclouddev.crm.core.entity.UserInfo;
import org.springclouddev.crm.core.servlet.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zhangzhiwei
 * @since 2020-04-27
 */
public interface AdminUserMapper extends BaseMapper<AdminUser> {
    /**
     * 根据用户名查询
     *
     * @param username username
     * @return info
     */
    List<Map<String,Object>> findByUsername(@Param("username") String username);

    /**
     * 查询用户列表
     * @param adminUserBO data
     * @param page 分页参数
     * @return data
     */
    BasePage<AdminUserVO> queryUserList(BasePage<AdminUserVO> page, @Param("data") AdminUserBO adminUserBO);

    /**
     * 查询通讯录
     * @param page
     * @param userBookBO
     * @return
     */
    BasePage<UserBookVO> queryListName(BasePage<UserBookVO> page, @Param("data") UserBookBO userBookBO);

    List<HrmSimpleUserVO> querySimpleUserByDeptId(Integer deptId);

    UserInfo queryLoginUserInfo(@Param("userId") Long userId);

    @SqlParser(filter = true)
    List<AdminUser> queryByUserName(@Param("username") String phone);
}
