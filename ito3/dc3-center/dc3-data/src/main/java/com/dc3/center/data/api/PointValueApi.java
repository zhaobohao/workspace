

package com.dc3.center.data.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.data.feign.PointValueClient;
import com.dc3.center.data.service.PointValueService;
import com.dc3.common.bean.R;
import com.dc3.common.bean.driver.PointValue;
import com.dc3.common.bean.driver.PointValueDto;
import com.dc3.common.constant.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author pnoker
 */
@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_DATA_URL_PREFIX)
public class PointValueApi implements PointValueClient {
    @Resource
    private PointValueService pointValueService;

    @Override
    public R<Page<PointValue>> list(PointValueDto pointValueDto) {
        try {
            Page<PointValue> page = pointValueService.list(pointValueDto);
            if (null != page) {
                return R.ok(page);
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
                return R.ok(pointValue);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<String> status(Long deviceId) {
        try {
            String status = pointValueService.status(deviceId);
            return R.ok(status, "ok");
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<String> realtime(Long deviceId, Long pointId) {
        try {
            String value = pointValueService.realtime(deviceId, pointId);
            if (null != value) {
                return R.ok(value, "ok");
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }
}