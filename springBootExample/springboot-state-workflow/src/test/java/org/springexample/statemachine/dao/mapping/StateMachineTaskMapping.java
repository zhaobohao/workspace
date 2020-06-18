package org.springexample.statemachine.dao.mapping;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springexample.statemachine.workflow.model.StateMachineTask;

@Mapper
public interface StateMachineTaskMapping extends BaseMapper<StateMachineTask> {
}
