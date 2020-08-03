package com.dc3.center.auth.service;

import com.dc3.common.base.Service;
import com.dc3.common.dto.BlackIpDto;
import com.dc3.common.model.BlackIp;

/**
 * User Interface
 *
 * @author pnoker
 */
public interface BlackIpService extends Service<BlackIp, BlackIpDto> {
    /**
     * 根据 Ip 查询 BlackIp
     *
     * @param ip
     * @return BlackIp
     */
    BlackIp selectByIp(String ip);

    /**
     * 根据 Ip 是否在Ip黑名单列表
     *
     * @param ip
     * @return boolean
     */
    boolean checkBlackIpValid(String ip);
}
