package org.springclouddev.iot.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.manager.dto.DeviceDto;
import org.springclouddev.iot.manager.entity.Device;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class DeviceClientFallback implements DeviceClient {
    String message = "No available server for client: DC3-MANAGER";

    @Override
    public R<Device> add(Device device) {
        return R.fail(message);
    }

    @Override
    public R<Boolean> delete(Long id) {
        return R.fail(message);
    }

    @Override
    public R<Device> update(Device device) {
        return R.fail(message);
    }

    @Override
    public R<Device> selectById(Long id) {
        return R.fail(message);
    }

    @Override
    public R<Map<Long, String>> deviceStatus(DeviceDto deviceDto) {
        return null;
    }

    @Override
    public R<Page<Device>> list(DeviceDto deviceDto) {
        return R.fail(message);
    }


}