package org.springclouddev.iot.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.manager.dto.DriverDto;
import org.springclouddev.iot.manager.entity.Driver;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class DriverClientFallback implements DriverClient {

    String message = "No available server for client: DC3-MANAGER";

    public R<Driver> add(Driver driver) {
        return R.fail(message);
    }

    @Override
    public R<Boolean> delete(Long id) {
        return R.fail(message);
    }

    @Override
    public R<Driver> update(Driver driver) {
        return R.fail(message);
    }

    @Override
    public R<Driver> selectById(Long id) {
        return R.fail(message);
    }

    @Override
    public R<Driver> selectByServiceName(String serviceName) {
        return R.fail(message);
    }

    @Override
    public R<Driver> selectByHostPort(String host, Integer port) {
        return R.fail(message);
    }

    @Override
    public R<Map<String, Boolean>> driverStatus(DriverDto driverDto) {
        return R.fail(message);
    }

    @Override
    public R<Page<Driver>> list(DriverDto driverDto) {
        return R.fail(message);
    }

}