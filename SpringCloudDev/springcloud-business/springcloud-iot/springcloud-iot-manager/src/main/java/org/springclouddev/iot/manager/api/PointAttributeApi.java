package org.springclouddev.iot.manager.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.manager.dto.PointAttributeDto;
import org.springclouddev.iot.manager.entityintAttribute;
import org.springclouddev.iot.manager.feign.PointAttributeClient;
import org.springclouddev.iot.manager.service.PointAttributeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>驱动属性配置信息 Client 接口实现
 */
@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_MANAGER_POINT_ATTRIBUTE_URL_PREFIX)
public class PointAttributeApi implements PointAttributeClient {
    @Resource
    private PointAttributeService pointAttributeService;

    @Override
    public R<PointAttribute> add(PointAttribute pointAttribute) {
        try {
            PointAttribute add = pointAttributeService.add(pointAttribute);
            if (null != add) {
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
            return pointAttributeService.delete(id) ? R.success("ok") : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<PointAttribute> update(PointAttribute pointAttribute) {
        try {
            PointAttribute update = pointAttributeService.update(pointAttribute);
            if (null != update) {
                return R.data(update);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<PointAttribute> selectById(Long id) {
        try {
            PointAttribute select = pointAttributeService.selectById(id);
            if (null != select) {
                return R.data(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Page<PointAttribute>> list(PointAttributeDto pointAttributeDto) {
        try {
            Page<PointAttribute> page = pointAttributeService.list(pointAttributeDto);
            if (null != page) {
                return R.data(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

}
