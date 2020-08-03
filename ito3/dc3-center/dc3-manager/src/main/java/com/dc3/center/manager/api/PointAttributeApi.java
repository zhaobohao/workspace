package com.dc3.center.manager.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.feign.PointAttributeClient;
import com.dc3.center.manager.service.PointAttributeService;
import com.dc3.common.bean.R;
import com.dc3.common.constant.Common;
import com.dc3.common.dto.PointAttributeDto;
import com.dc3.common.model.PointAttribute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>驱动属性配置信息 Client 接口实现
 *
 * @author pnoker
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
            return pointAttributeService.delete(id) ? R.ok() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<PointAttribute> update(PointAttribute pointAttribute) {
        try {
            PointAttribute update = pointAttributeService.update(pointAttribute);
            if (null != update) {
                return R.ok(update);
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
                return R.ok(select);
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
                return R.ok(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

}
