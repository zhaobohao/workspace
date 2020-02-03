package com.mallplus.marking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallplus.common.entity.fenxiao.entity.FenxiaoUserRelate;
import com.mallplus.marking.mapper.SmsFenxiaoUserRelateMapper;
import com.mallplus.marking.service.ISmsFenxiaoUserRelateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
* @author mallplus
* @date 2019-12-17
*/
@Service
public class SmsFenxiaoUserRelateServiceImpl extends ServiceImpl
<SmsFenxiaoUserRelateMapper, FenxiaoUserRelate> implements ISmsFenxiaoUserRelateService {

@Resource
private SmsFenxiaoUserRelateMapper fenxiaoUserRelateMapper;


}
