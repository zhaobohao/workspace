package org.springclouddev.iot.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.manager.dto.ProfileDto;
import org.springclouddev.iot.manager.entity.Profile;
import org.springframework.stereotype.Component;

/**
 * <p>ProfileClientHystrix
 */
@Slf4j
@Component
public class ProfileClientFallback implements ProfileClient {

    String message = "No available server for client: DC3-MANAGER";

    @Override
    public R<Profile> add(Profile profile) {
        return R.fail(message);
    }

    @Override
    public R<Boolean> delete(Long id) {
        return R.fail(message);
    }

    @Override
    public R<Profile> update(Profile profile) {
        return R.fail(message);
    }

    @Override
    public R<Profile> selectById(Long id) {
        return R.fail(message);
    }

    @Override
    public R<Page<Profile>> list(ProfileDto profileDto) {
        return R.fail(message);
    }

}