package com.transcation.service.base;

import com.transcation.service.enums.ServiceStatus;

/**
 * create by: zhaobo
 * description: TODO
 * create time: 2020/6/10 13:13
 *
 * @Param: null
 * @return
 */
public interface BaseTranscationService {

    /**
     *  正向交易
      * @return
     */
    ServiceStatus trade(BaseServiceContext context);

    /**
      * 冲正交易
      * @return
      */
    ServiceStatus refund(BaseServiceContext context);

     /**
      * 查证流程
      * @return
      */
     ServiceStatus  check(BaseServiceContext context);
}
