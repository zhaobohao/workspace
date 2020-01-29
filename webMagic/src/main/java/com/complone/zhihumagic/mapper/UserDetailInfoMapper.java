package com.complone.zhihumagic.mapper;

import com.complone.zhihumagic.model.UserBaseInfo;
import com.complone.zhihumagic.model.UserDetailInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface UserDetailInfoMapper extends Mapper<UserDetailInfo> {
    List<UserDetailInfo> getBusinessStatic(Integer selectLimitAmount);

    List<UserDetailInfo> getEmploymentStatic(Integer selectLimitAmount);

    List<UserDetailInfo> getEducationStatic(Integer selectLimitAmount);

    int insertOne(UserDetailInfo userDetailInfo);
}
