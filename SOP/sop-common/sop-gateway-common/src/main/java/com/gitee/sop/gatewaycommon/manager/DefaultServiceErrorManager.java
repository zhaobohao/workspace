package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.bean.ErrorDefinition;
import com.gitee.sop.gatewaycommon.bean.ErrorEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tanghc
 */
public class DefaultServiceErrorManager implements ServiceErrorManager {

    private static Map<String, ErrorEntity> store = new ConcurrentHashMap<>(128);

    @Override
    public Collection<ErrorEntity> listAllErrors() {
        return store.values();
    }

    @Override
    public void saveBizError(ErrorDefinition errorDefinition) {

    }

    @Override
    public void saveUnknownError(ErrorDefinition errorDefinition) {
        boolean hasCapacity = store.size() < ApiConfig.getInstance().getStoreErrorCapacity();
        // 这里还可以做其它事情，比如错误量到达一定数目后，自动发送邮件/微信给开发人员，方便及时获取异常情况
        String id = this.buildId(errorDefinition);
        ErrorEntity errorEntity = store.get(id);
        if (errorEntity == null && hasCapacity) {
            errorEntity = new ErrorEntity();
            BeanUtils.copyProperties(errorDefinition, errorEntity);
            errorEntity.setId(id);
            store.put(id, errorEntity);
        }
        if (errorEntity != null) {
            errorEntity.setCount(errorEntity.getCount() + 1);
        }
    }

    @Override
    public void clear() {
        store.clear();
    }

    protected String buildId(ErrorDefinition errorDefinition) {
        return DigestUtils.md5Hex(errorDefinition.getServiceId() + errorDefinition.getErrorMsg());
    }

}
