
package org.springclouddev.core.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springclouddev.core.log.mapper.LogErrorMapper;
import org.springclouddev.core.log.model.LogError;
import org.springclouddev.core.log.service.ILogErrorService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author zhaobohao
 * @since 2018-09-26
 */
@Service
public class LogErrorServiceImpl extends ServiceImpl<LogErrorMapper, LogError> implements ILogErrorService {

}
