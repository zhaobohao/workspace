package org.springclouddev.iot.manager.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springclouddev.iot.manager.dto.PointDto;
import org.springclouddev.iot.manager.entity.Point;
import org.springclouddev.iot.manager.feign.PointClient;
import org.springclouddev.iot.manager.service.NotifyService;
import org.springclouddev.iot.manager.service.PointService;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.common.constant.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>位号 Client 接口实现
 *

 */
@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_MANAGER_POINT_URL_PREFIX)
public class PointApi implements PointClient {

    @Resource
    private PointService pointService;
    @Resource
    private NotifyService notifyService;

    @Override
    public R<Point> add(Point point) {
        try {
            Point add = pointService.add(point);
            if (null != add) {
                notifyService.notifyDriverPoint(point.getId(), point.getProfileId(), Operation.Point.ADD);
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
            Point point = pointService.selectById(id);
            if (pointService.delete(id)) {
                notifyService.notifyDriverPoint(point.getId(), point.getProfileId(), Operation.Point.DELETE);
                return R.success("ok");
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Point> update(Point point) {
        try {
            Point update = pointService.update(point);
            if (null != update) {
                notifyService.notifyDriverPoint(point.getId(), point.getProfileId(), Operation.Point.UPDATE);
                return R.data(update);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Point> selectById(Long id) {
        try {
            Point select = pointService.selectById(id);
            if (null != select) {
                return R.data(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Page<Point>> list(PointDto pointDto) {
        try {
            Page<Point> page = pointService.list(pointDto);
            if (null != page) {
                return R.data(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

}
