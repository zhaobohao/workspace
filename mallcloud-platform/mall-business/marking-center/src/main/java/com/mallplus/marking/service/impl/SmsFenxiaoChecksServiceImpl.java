package com.mallplus.marking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallplus.common.entity.fenxiao.entity.FenxiaoChecks;
import com.mallplus.marking.mapper.SmsFenxiaoChecksMapper;
import com.mallplus.marking.service.ISmsFenxiaoChecksService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
* @author mallplus
* @date 2019-12-17
*/
@Service
public class SmsFenxiaoChecksServiceImpl extends ServiceImpl
<SmsFenxiaoChecksMapper, FenxiaoChecks> implements ISmsFenxiaoChecksService {

@Resource
private SmsFenxiaoChecksMapper fenxiaoChecksMapper;


}
