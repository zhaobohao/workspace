package org.springclouddev.iot.manager.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springclouddev.iot.manager.dto.DeviceDto;
import org.springclouddev.iot.manager.entity.Device;
import org.springclouddev.iot.manager.feign.DeviceClient;
import org.springclouddev.iot.manager.service.DeviceService;
import org.springclouddev.iot.manager.service.NotifyService;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.common.constant.Operation;
import org.springclouddev.iot.manager.dto.DeviceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>设备 Client 接口实现
 */
@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_MANAGER_DEVICE_URL_PREFIX)
public class DeviceApi implements DeviceClient {
    @Resource
    private DeviceService deviceService;
    @Resource
    private NotifyService notifyService;

    @Override
    public R<Device> add(Device device) {
        try {
            Device add = deviceService.add(device);
            if (null != add) {
                notifyService.notifyDriverDevice(device.getId(), device.getProfileId(), Operation.Device.ADD);
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
            Device device = deviceService.selectById(id);
            if (deviceService.delete(id)) {
                notifyService.notifyDriverDevice(device.getId(), device.getProfileId(), Operation.Device.DELETE);
                return R.success("ok");
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Device> update(Device device) {
        try {
            Device update = deviceService.update(device);
            if (null != update) {
                notifyService.notifyDriverDevice(device.getId(), device.getProfileId(), Operation.Device.UPDATE);
                return R.data(update);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Device> selectById(Long id) {
        try {
            Device select = deviceService.selectById(id);
            if (null != select) {
                return R.data(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Map<Long, String>> deviceStatus(DeviceDto deviceDto) {
        try {
            Map<Long, String> deviceStatuses = deviceService.deviceStatus(deviceDto);
            return R.data(deviceStatuses);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Page<Device>> list(DeviceDto deviceDto) {
        try {
            Page<Device> page = deviceService.list(deviceDto);
            if (null != page) {
                return R.data(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

}
