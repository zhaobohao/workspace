
package org.springbootdev.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springbootdev.core.log.model.LogError;
import org.springbootdev.modules.system.mapper.LogErrorMapper;
import org.springbootdev.modules.system.service.ILogErrorService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author merryChen
 */
@Service
public class LogErrorServiceImpl extends ServiceImpl<LogErrorMapper, LogError> implements ILogErrorService {

}
