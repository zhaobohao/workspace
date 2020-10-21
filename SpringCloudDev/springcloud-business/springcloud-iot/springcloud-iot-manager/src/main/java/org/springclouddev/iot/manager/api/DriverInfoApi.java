package org.springclouddev.iot.manager.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.common.constant.Operation;
import org.springclouddev.iot.manager.dto.DriverInfoDto;
import org.springclouddev.iot.manager.entity.DriverInfo;
import org.springclouddev.iot.manager.feign.DriverInfoClient;
import org.springclouddev.iot.manager.service.DriverInfoService;
import org.springclouddev.iot.manager.service.NotifyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>位号配置信息 Client 接口实现
 *

 */
@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_MANAGER_DRIVER_INFO_URL_PREFIX)
public class DriverInfoApi implements DriverInfoClient {

    @Resource
    private DriverInfoService driverInfoService;
    @Resource
    private NotifyService notifyService;

    @Override
    public R<DriverInfo> add(DriverInfo driverInfo) {
        try {
            DriverInfo add = driverInfoService.add(driverInfo);
            if (null != add) {
                notifyService.notifyDriverDriverInfo(driverInfo.getId(), driverInfo.getDriverAttributeId(), driverInfo.getProfileId(), Operation.DriverInfo.ADD);
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
            DriverInfo driverInfo = driverInfoService.selectById(id);
            if (driverInfoService.delete(id)) {
                notifyService.notifyDriverDriverInfo(driverInfo.getId(), driverInfo.getDriverAttributeId(), driverInfo.getProfileId(), Operation.DriverInfo.DELETE);
                return R.success("ok");
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<DriverInfo> update(DriverInfo driverInfo) {
        try {
            DriverInfo update = driverInfoService.update(driverInfo);
            if (null != update) {
                notifyService.notifyDriverDriverInfo(driverInfo.getId(), driverInfo.getDriverAttributeId(), driverInfo.getProfileId(), Operation.DriverInfo.UPDATE);
                return R.data(update);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<DriverInfo> selectById(Long id) {
        try {
            DriverInfo select = driverInfoService.selectById(id);
            if (null != select) {
                return R.data(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Page<DriverInfo>> list(DriverInfoDto driverInfoDto) {
        try {
            Page<DriverInfo> page = driverInfoService.list(driverInfoDto);
            if (null != page) {
                return R.data(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

}
