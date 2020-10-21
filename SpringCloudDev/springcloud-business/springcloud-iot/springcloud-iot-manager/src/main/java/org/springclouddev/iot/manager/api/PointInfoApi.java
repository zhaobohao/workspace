package org.springclouddev.iot.manager.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.common.constant.Operation;
import org.springclouddev.iot.manager.dto.PointInfoDto;
import org.springclouddev.iot.manager.entity.PointInfo;
import org.springclouddev.iot.manager.feign.PointInfoClient;
import org.springclouddev.iot.manager.service.NotifyService;
import org.springclouddev.iot.manager.service.PointInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>位号配置信息 Client 接口实现
 *

 */
@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_MANAGER_POINT_INFO_URL_PREFIX)
public class PointInfoApi implements PointInfoClient {

    @Resource
    private PointInfoService pointInfoService;
    @Resource
    private NotifyService notifyService;

    @Override
    public R<PointInfo> add(PointInfo pointInfo) {
        try {
            PointInfo add = pointInfoService.add(pointInfo);
            if (null != add) {
                notifyService.notifyDriverPointInfo(pointInfo.getId(), pointInfo.getPointAttributeId(), pointInfo.getDeviceId(), Operation.PointInfo.ADD);
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
            PointInfo pointInfo = pointInfoService.selectById(id);
            if (pointInfoService.delete(id)) {
                notifyService.notifyDriverPointInfo(pointInfo.getPointId(), pointInfo.getPointAttributeId(), pointInfo.getDeviceId(), Operation.PointInfo.DELETE);
                return R.success("ok");
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<PointInfo> update(PointInfo pointInfo) {
        try {
            PointInfo update = pointInfoService.update(pointInfo);
            if (null != update) {
                notifyService.notifyDriverPointInfo(pointInfo.getId(), pointInfo.getPointAttributeId(), pointInfo.getDeviceId(), Operation.PointInfo.UPDATE);
                return R.data(update);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<PointInfo> selectById(Long id) {
        try {
            PointInfo select = pointInfoService.selectById(id);
            if (null != select) {
                return R.data(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Page<PointInfo>> list(PointInfoDto pointInfoDto) {
        try {
            Page<PointInfo> page = pointInfoService.list(pointInfoDto);
            if (null != page) {
                return R.data(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

}
