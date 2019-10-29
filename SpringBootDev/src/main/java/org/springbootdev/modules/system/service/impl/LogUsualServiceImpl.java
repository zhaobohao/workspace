
package org.springbootdev.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springbootdev.core.log.model.LogUsual;
import org.springbootdev.modules.system.mapper.LogUsualMapper;
import org.springbootdev.modules.system.service.ILogUsualService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author merryChen
 */
@Service
public class LogUsualServiceImpl extends ServiceImpl<LogUsualMapper, LogUsual> implements ILogUsualService {

}
