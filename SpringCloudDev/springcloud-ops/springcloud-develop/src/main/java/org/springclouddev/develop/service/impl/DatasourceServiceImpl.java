
package org.springclouddev.develop.service.impl;

import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.develop.entity.Datasource;
import org.springclouddev.develop.mapper.DatasourceMapper;
import org.springclouddev.develop.service.IDatasourceService;
import org.springframework.stereotype.Service;

/**
 * 数据源配置表 服务实现类
 *
 * @author firewan
 */
@Service
public class DatasourceServiceImpl extends BaseServiceImpl<DatasourceMapper, Datasource> implements IDatasourceService {

}
