package com.dc3.transfer.rtmp.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.transfer.rtmp.feign.RtmpClient;
import com.dc3.common.bean.R;
import com.dc3.common.constant.Common;
import com.dc3.common.dto.RtmpDto;
import com.dc3.common.model.Rtmp;
import com.dc3.transfer.rtmp.service.RtmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Rest接口控制器
 *

 */
@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_RTMP_URL_PREFIX)
public class RtmpApi implements RtmpClient {
    @Resource
    private RtmpService rtmpService;

    @Override
    public R<Rtmp> add(Rtmp rtmp) {
        try {
            Rtmp add = rtmpService.add(rtmp);
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
            return rtmpService.delete(id) ? R.ok() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Rtmp> update(Rtmp rtmp) {
        try {
            Rtmp update = rtmpService.update(rtmp);
            if (null != update) {
                return R.ok(update);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Rtmp> selectById(Long id) {
        try {
            Rtmp select = rtmpService.selectById(id);
            if (null != select) {
                return R.ok(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Page<Rtmp>> list(RtmpDto rtmpDto) {
        try {
            Page<Rtmp> page = rtmpService.list(rtmpDto);
            if (null != page) {
                return R.ok(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Boolean> start(Long id) {
        try {
            return rtmpService.start(id) ? R.ok() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Boolean> stop(Long id) {
        try {
            return rtmpService.stop(id) ? R.ok() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

}
