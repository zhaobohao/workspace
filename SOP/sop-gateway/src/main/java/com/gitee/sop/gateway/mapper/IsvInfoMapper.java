package com.gitee.sop.gateway.mapper;

import com.gitee.fastmybatis.core.mapper.CrudMapper;
import com.gitee.sop.gateway.entity.IsvDetailDTO;
import com.gitee.sop.gateway.entity.IsvInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author tanghc
 */
public interface IsvInfoMapper extends CrudMapper<IsvInfo, Long> {

    /**
     * 获取所有的isv信息
     * @return 所有的isv信息
     */
    @Select("SELECT  " +
            "  t.app_key appKey " +
            "  ,t.status " +
            "  ,t2.sign_type signType " +
            "  ,t2.secret " +
            "  ,t2.public_key_isv publicKeyIsv " +
            "  ,t2.private_key_platform privateKeyPlatform " +
            "FROM isv_info t " +
            "INNER JOIN isv_keys t2 ON t.app_key = t2.app_key")
    List<IsvDetailDTO> listIsvDetail();
}
