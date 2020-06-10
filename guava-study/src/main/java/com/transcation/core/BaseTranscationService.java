package com.transcation.core;

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
     Boolean trade();

     /**
      * 冲正交易
      * @return
      */
     Boolean refund();

     /**
      * 查证流程
      * @return
      */
     Boolean  check();
}
