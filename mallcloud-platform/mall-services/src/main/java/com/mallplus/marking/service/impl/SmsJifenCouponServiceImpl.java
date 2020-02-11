package com.mallplus.marking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallplus.common.entity.jifen.entity.JifenCoupon;
import com.mallplus.marking.mapper.SmsJifenCouponMapper;
import com.mallplus.marking.service.ISmsJifenCouponService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
* @author mallplus
* @date 2019-12-17
*/
@Service
public class SmsJifenCouponServiceImpl extends ServiceImpl
<SmsJifenCouponMapper, JifenCoupon> implements ISmsJifenCouponService {

@Resource
private SmsJifenCouponMapper jifenCouponMapper;


}
