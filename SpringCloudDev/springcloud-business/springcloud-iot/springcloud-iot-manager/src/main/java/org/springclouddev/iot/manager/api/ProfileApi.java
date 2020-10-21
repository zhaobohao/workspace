package org.springclouddev.iot.manager.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.common.constant.Operation;
import org.springclouddev.iot.manager.dto.ProfileDto;
import org.springclouddev.iot.manager.entity.Driver;
import org.springclouddev.iot.manager.entity.Profile;
import org.springclouddev.iot.manager.feign.ProfileClient;
import org.springclouddev.iot.manager.service.DriverService;
import org.springclouddev.iot.manager.service.NotifyService;
import org.springclouddev.iot.manager.service.ProfileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>模板 Client 接口实现
 */
@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_MANAGER_PROFILE_URL_PREFIX)
public class ProfileApi implements ProfileClient {

    @Resource
    private DriverService driverService;
    @Resource
    private ProfileService profileService;
    @Resource
    private NotifyService notifyService;

    @Override
    public R<Profile> add(Profile profile) {
        try {
            Profile add = profileService.add(profile);
            if (null != add) {
                Driver driver = driverService.selectByProfileId(profile.getId());
                notifyService.notifyDriverProfile(driver, profile.getId(), Operation.Profile.ADD);
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
            Driver driver = driverService.selectByProfileId(id);
            if (profileService.delete(id)) {
                notifyService.notifyDriverProfile(driver, id, Operation.Profile.DELETE);
                return R.success("ok");
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Profile> update(Profile profile) {
        try {
            Profile update = profileService.update(profile);
            if (null != update) {
                return R.data(update);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Profile> selectById(Long id) {
        try {
            Profile select = profileService.selectById(id);
            if (null != select) {
                return R.data(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Page<Profile>> list(ProfileDto profileDto) {
        try {
            Page<Profile> page = profileService.list(profileDto);
            if (null != page) {
                return R.data(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

}
