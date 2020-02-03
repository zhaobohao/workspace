package com.mallplus.marking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallplus.common.entity.jifen.entity.JifenSignRule;
import com.mallplus.marking.mapper.SmsJifenSignRuleMapper;
import com.mallplus.marking.service.ISmsJifenSignRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
* @author mallplus
* @date 2019-12-17
*/
@Service
public class SmsJifenSignRuleServiceImpl extends ServiceImpl
<SmsJifenSignRuleMapper, JifenSignRule> implements ISmsJifenSignRuleService {

@Resource
private SmsJifenSignRuleMapper jifenSignRuleMapper;


}
