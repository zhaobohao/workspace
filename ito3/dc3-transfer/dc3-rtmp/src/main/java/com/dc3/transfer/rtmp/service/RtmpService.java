package com.dc3.transfer.rtmp.service;

import com.dc3.common.base.Service;
import com.dc3.common.dto.RtmpDto;
import com.dc3.common.model.Rtmp;


public interface RtmpService extends Service<Rtmp, RtmpDto> {

    /**
     * 启动
     *
     * @param id Id
     * @return boolean
     */
    boolean start(Long id);

    /**
     * 停止
     *
     * @param id Id
     * @return boolean
     */
    boolean stop(Long id);
}
