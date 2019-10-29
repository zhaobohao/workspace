
package org.springbootdev.modules.develop.service.impl;

import org.springbootdev.core.mp.base.BaseServiceImpl;
import org.springbootdev.modules.develop.entity.Datasource;
import org.springbootdev.modules.develop.mapper.DatasourceMapper;
import org.springbootdev.modules.develop.service.IDatasourceService;
import org.springframework.stereotype.Service;

/**
 * 数据源配置表 服务实现类
 *
 * @author merryChen
 */
@Service
public class DatasourceServiceImpl extends BaseServiceImpl<DatasourceMapper, Datasource> implements IDatasourceService {

}
