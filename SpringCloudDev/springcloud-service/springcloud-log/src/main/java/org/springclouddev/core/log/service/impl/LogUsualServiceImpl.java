
package org.springclouddev.core.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springclouddev.core.log.mapper.LogUsualMapper;
import org.springclouddev.core.log.model.LogUsual;
import org.springclouddev.core.log.service.ILogUsualService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author firewan
 * @since 2018-10-12
 */
@Service
public class LogUsualServiceImpl extends ServiceImpl<LogUsualMapper, LogUsual> implements ILogUsualService {

}
