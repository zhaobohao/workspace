package com.mallplus.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mallplus.common.entity.ums.UmsMember;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface UmsMemberMapper extends BaseMapper<UmsMember> {
    @Update("update ums_member set integration=integration-#{param2} where id = #{param1}")
    void substractIntegrationById(Long id,int substract);

    @Update("update ums_member set blance=blance-#{param2} where id = #{param1}")
    void substractBlanceByid(Long id, int payAmount);

    @Update("update ums_member set blance=blance+#{param2} where id = #{param1}")
    void addBlanceByid(Long id, int payAmount);
}
