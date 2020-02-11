package com.mallplus.marking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallplus.common.entity.fenxiao.entity.FenxiaoConfig;
import com.mallplus.marking.mapper.SmsFenxiaoConfigMapper;
import com.mallplus.marking.service.ISmsFenxiaoConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
* @author mallplus
* @date 2019-12-17
*/
@Service
public class SmsFenxiaoConfigServiceImpl extends ServiceImpl
<SmsFenxiaoConfigMapper, FenxiaoConfig> implements ISmsFenxiaoConfigService {

@Resource
private SmsFenxiaoConfigMapper fenxiaoConfigMapper;


}
