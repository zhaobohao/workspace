package org.springclouddev.iot.manager.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springclouddev.iot.manager.dto.DriverDto;
import org.springclouddev.iot.manager.entity.Driver;
import org.springclouddev.iot.manager.feign.DriverClient;
import org.springclouddev.iot.manager.service.DriverService;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>驱动 Client 接口实现
 *

 */
@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_MANAGER_DRIVER_URL_PREFIX)
public class DriverApi implements DriverClient {
    @Resource
    private DriverService driverService;

    @Override
    public R<Driver> add(Driver driver) {
        try {
            Driver add = driverService.add(driver);
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
            return driverService.delete(id) ? R.success("ok") : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Driver> update(Driver driver) {
        try {
            Driver update = driverService.update(driver);
            if (null != update) {
                return R.data(update);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Driver> selectById(Long id) {
        try {
            Driver select = driverService.selectById(id);
            return R.data(select);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Driver> selectByServiceName(String serviceName) {
        try {
            Driver select = driverService.selectByServiceName(serviceName);
            return R.data(select);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Driver> selectByHostPort(String host, Integer port) {
        try {
            Driver select = driverService.selectByHostPort(host, port);
            return R.data(select);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Map<String, Boolean>> driverStatus(DriverDto driverDto) {
        try {
            Map<String, Boolean> driverStatuses = driverService.driverStatus(driverDto);
            return R.data(driverStatuses);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Page<Driver>> list(DriverDto driverDto) {
        try {
            Page<Driver> page = driverService.list(driverDto);
            if (null != page) {
                return R.data(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

}
