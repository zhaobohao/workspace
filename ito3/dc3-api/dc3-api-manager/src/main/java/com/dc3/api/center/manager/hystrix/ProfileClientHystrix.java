

package com.dc3.api.center.manager.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.feign.ProfileClient;
import com.dc3.common.bean.R;
import com.dc3.common.dto.ProfileDto;
import com.dc3.common.model.Profile;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>ProfileClientHystrix
 *
 * @author pnoker
 */
@Slf4j
@Component
public class ProfileClientHystrix implements FallbackFactory<ProfileClient> {

    @Override
    public ProfileClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-MANAGER" : throwable.getMessage();
        log.error("Hystrix:{}", message);

        return new ProfileClient() {

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

        };
    }
}