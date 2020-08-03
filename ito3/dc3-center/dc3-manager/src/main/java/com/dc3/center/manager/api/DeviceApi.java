package com.dc3.center.manager.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.feign.DeviceClient;
import com.dc3.center.manager.service.DeviceService;
import com.dc3.center.manager.service.NotifyService;
import com.dc3.common.bean.R;
import com.dc3.common.constant.Common;
import com.dc3.common.constant.Operation;
import com.dc3.common.dto.DeviceDto;
import com.dc3.common.model.Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>设备 Client 接口实现
 *
 * @author pnoker
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
                return R.ok(add);
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
                return R.ok();
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
                return R.ok(update);
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
                return R.ok(select);
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
            return R.ok(deviceStatuses);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Page<Device>> list(DeviceDto deviceDto) {
        try {
            Page<Device> page = deviceService.list(deviceDto);
            if (null != page) {
                return R.ok(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

}
