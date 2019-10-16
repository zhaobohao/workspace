package com.gitee.sop.adminserver.mapper;

import com.gitee.fastmybatis.core.mapper.CrudMapper;

import com.gitee.sop.adminserver.api.isv.result.IsvDetailDTO;
import com.gitee.sop.adminserver.entity.IsvInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * @author tanghc
 */
public interface IsvInfoMapper extends CrudMapper<IsvInfo, Long> {

    /**
     * 获取isv详细信息
     * @param appKey appKey
     * @return 返回详细信息，没有返回null
     */
    @Select("SELECT  " +
            "  t.app_key appKey " +
            "  ,t.status " +
            "  ,t2.sign_type signType " +
            "  ,t2.secret " +
            "  ,t2.public_key_isv publicKeyIsv " +
            "  ,t2.private_key_platform privateKeyPlatform " +
            "FROM isv_info t " +
            "INNER JOIN isv_keys t2 ON t.app_key = t2.app_key " +
            "WHERE t.app_key = #{appKey}")
    IsvDetailDTO getIsvDetail(@Param("appKey") String appKey);

}
