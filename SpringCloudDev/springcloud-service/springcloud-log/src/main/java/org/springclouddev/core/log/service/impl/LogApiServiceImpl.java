
package org.springclouddev.core.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springclouddev.core.log.mapper.LogApiMapper;
import org.springclouddev.core.log.model.LogApi;
import org.springclouddev.core.log.service.ILogApiService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author firewan
 */
@Service
public class LogApiServiceImpl extends ServiceImpl<LogApiMapper, LogApi> implements ILogApiService {


}
