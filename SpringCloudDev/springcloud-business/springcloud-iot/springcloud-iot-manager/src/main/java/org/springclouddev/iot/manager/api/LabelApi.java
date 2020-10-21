package org.springclouddev.iot.manager.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.manager.dto.LabelDto;
import org.springclouddev.iot.manager.entity.Label;
import org.springclouddev.iot.manager.feign.LabelClient;
import org.springclouddev.iot.manager.service.LabelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>标签 Client 接口实现
 */
@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_MANAGER_LABEL_URL_PREFIX)
public class LabelApi implements LabelClient {
    @Resource
    private LabelService labelService;

    @Override
    public R<Label> add(Label label) {
        try {
            Label add = labelService.add(label);
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
            return labelService.delete(id) ? R.success("ok") : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Label> update(Label label) {
        try {
            Label update = labelService.update(label);
            if (null != update) {
                return R.data(update);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Label> selectById(Long id) {
        try {
            Label select = labelService.selectById(id);
            if (null != select) {
                return R.data(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Page<Label>> list(LabelDto labelDto) {
        try {
            Page<Label> page = labelService.list(labelDto);
            if (null != page) {
                return R.data(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }
}
