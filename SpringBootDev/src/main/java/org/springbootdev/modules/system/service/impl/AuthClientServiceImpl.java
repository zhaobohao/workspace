
package org.springbootdev.modules.system.service.impl;

import org.springbootdev.core.mp.base.BaseServiceImpl;
import org.springbootdev.modules.system.entity.AuthClient;
import org.springbootdev.modules.system.mapper.AuthClientMapper;
import org.springbootdev.modules.system.service.IAuthClientService;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author zhaobohao
 */
@Service
public class AuthClientServiceImpl extends BaseServiceImpl<AuthClientMapper, AuthClient> implements IAuthClientService {

}
