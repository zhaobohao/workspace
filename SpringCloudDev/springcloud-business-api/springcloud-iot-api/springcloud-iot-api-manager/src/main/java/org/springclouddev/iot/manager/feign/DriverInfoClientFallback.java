package org.springclouddev.iot.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.manager.dto.DriverInfoDto;
import org.springclouddev.iot.manager.entity.DriverInfo;
import org.springframework.stereotype.Component;

/**
 * <p>DriverInfoClientHystrix
 */
@Slf4j
@Component
public class DriverInfoClientFallback implements DriverInfoClient {

    String message = "No available server for client: DC3-MANAGER";

    @Override
    public R<DriverInfo> add(DriverInfo driverInfo) {
        return R.fail(message);
    }

    @Override
    public R<Boolean> delete(Long id) {
        return R.fail(message);
    }

    @Override
    public R<DriverInfo> update(DriverInfo driverInfo) {
        return R.fail(message);
    }

    @Override
    public R<DriverInfo> selectById(Long id) {
        return R.fail(message);
    }

    @Override
    public R<Page<DriverInfo>> list(DriverInfoDto driverInfoDto) {
        return R.fail(message);
    }

}