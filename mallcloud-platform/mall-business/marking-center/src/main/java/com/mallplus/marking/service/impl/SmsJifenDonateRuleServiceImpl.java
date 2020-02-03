package com.mallplus.marking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallplus.common.entity.jifen.entity.JifenDonateRule;
import com.mallplus.marking.mapper.SmsJifenDonateRuleMapper;
import com.mallplus.marking.service.ISmsJifenDonateRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
* @author mallplus
* @date 2019-12-17
*/
@Service
public class SmsJifenDonateRuleServiceImpl extends ServiceImpl
<SmsJifenDonateRuleMapper, JifenDonateRule> implements ISmsJifenDonateRuleService {

@Resource
private SmsJifenDonateRuleMapper jifenDonateRuleMapper;


}
