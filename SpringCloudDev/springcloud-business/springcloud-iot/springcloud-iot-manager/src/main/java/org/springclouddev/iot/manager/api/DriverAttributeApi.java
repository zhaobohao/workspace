package org.springclouddev.iot.manager.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.manager.dto.DriverAttributeDto;
import org.springclouddev.iot.manager.entity.DriverAttribute;
import org.springclouddev.iot.manager.feign.DriverAttributeClient;
import org.springclouddev.iot.manager.service.DriverAttributeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>驱动连接配置信息 Client 接口实现
 */
@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_MANAGER_DRIVER_ATTRIBUTE_URL_PREFIX)
public class DriverAttributeApi implements DriverAttributeClient {
    @Resource
    private DriverAttributeService driverAttributeService;

    @Override
    public R<DriverAttribute> add(DriverAttribute driverAttribute) {
        try {
            DriverAttribute add = driverAttributeService.add(driverAttribute);
            if (null != add) {
                return R.data(add);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Boolean> delete(Long id) {
        try {
            return driverAttributeService.delete(id) ? R.success("ok") : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<DriverAttribute> update(DriverAttribute driverAttribute) {
        try {
            DriverAttribute update = driverAttributeService.update(driverAttribute);
            if (null != update) {
                return R.data(update);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<DriverAttribute> selectById(Long id) {
        try {
            DriverAttribute select = driverAttributeService.selectById(id);
            if (null != select) {
                return R.data(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Page<DriverAttribute>> list(DriverAttributeDto driverAttributeDto) {
        try {
            Page<DriverAttribute> page = driverAttributeService.list(driverAttributeDto);
            if (null != page) {
                return R.data(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

}
