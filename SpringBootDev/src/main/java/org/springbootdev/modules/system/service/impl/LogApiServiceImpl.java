
package org.springbootdev.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springbootdev.core.log.model.LogApi;
import org.springbootdev.modules.system.mapper.LogApiMapper;
import org.springbootdev.modules.system.service.ILogApiService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author merryChen
 */
@Service
public class LogApiServiceImpl extends ServiceImpl<LogApiMapper, LogApi> implements ILogApiService {


}
