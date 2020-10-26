package org.springclouddev.iot.data.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.data.dto.PointValueDto;
import org.springclouddev.iot.data.entityintValue;
import org.springclouddev.iot.data.feign.PointValueClient;
import org.springclouddev.iot.data.service.PointValueService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = Common.Service.DC3_DATA_URL_PREFIX)
public class PointValueApi implements PointValueClient {

    @Resource
    private PointValueService pointValueService;

    @Override
    public R<String> status(Long deviceId) {
        try {
            String status = pointValueService.status(deviceId);
            return R.data(status, "ok");
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<List<PointValue>> realtime(@NotNull Long deviceId) {
        try {
            List<PointValue> pointValues = pointValueService.realtime(deviceId);
            if (null != pointValues) {
                return R.data(pointValues, "ok");
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<PointValue> realtime(Long deviceId, Long pointId) {
        try {
            PointValue pointValue = pointValueService.realtime(deviceId, pointId);
            if (null != pointValue) {
                return R.data(pointValue, "ok");
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<PointValue> latest(Long deviceId) {
        try {
            PointValue pointValue = pointValueService.latest(deviceId, null);
            if (null != pointValue) {
                return R.data(pointValue);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<PointValue> latest(Long deviceId, Long pointId) {
        try {
            PointValue pointValue = pointValueService.latest(deviceId, pointId);
            if (null != pointValue) {
                return R.data(pointValue);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<IPage<PointValue>> list(PointValueDto pointValueDto) {
        try {
            Page<PointValue> page = pointValueService.list(pointValueDto);
            if (null != page) {
                return R.data(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

}