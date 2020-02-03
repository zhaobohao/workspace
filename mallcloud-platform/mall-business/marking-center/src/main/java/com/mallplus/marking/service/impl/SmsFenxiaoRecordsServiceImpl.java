package com.mallplus.marking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallplus.common.entity.fenxiao.entity.FenxiaoRecords;
import com.mallplus.marking.mapper.SmsFenxiaoRecordsMapper;
import com.mallplus.marking.service.ISmsFenxiaoRecordsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
* @author mallplus
* @date 2019-12-17
*/
@Service
public class SmsFenxiaoRecordsServiceImpl extends ServiceImpl
<SmsFenxiaoRecordsMapper, FenxiaoRecords> implements ISmsFenxiaoRecordsService {

@Resource
private SmsFenxiaoRecordsMapper fenxiaoRecordsMapper;


}
