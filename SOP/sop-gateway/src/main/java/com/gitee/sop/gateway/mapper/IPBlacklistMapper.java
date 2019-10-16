package com.gitee.sop.gateway.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * IP黑名单
 * @author tanghc
 */
@Mapper
public interface IPBlacklistMapper {

    /**
     * 获取所有IP
     * @return
     */
    @Select("SELECT ip FROM config_ip_blacklist")
    List<String> listAllIP();

}
