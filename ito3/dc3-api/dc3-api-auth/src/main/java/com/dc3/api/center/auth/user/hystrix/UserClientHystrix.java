package com.dc3.api.center.auth.user.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.auth.user.feign.UserClient;
import com.dc3.common.bean.R;
import com.dc3.common.dto.UserDto;
import com.dc3.common.model.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * UserClientHystrix
 *

 */
@Slf4j
@Component
public class UserClientHystrix implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-AUTH" : throwable.getMessage();
        log.error("Hystrix:{}", message);

        return new UserClient() {

            @Override
            public R<User> add(User user) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> delete(Long id) {
                return R.fail(message);
            }

            @Override
            public R<User> update(User user) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> restPassword(Long id) {
                return R.fail(message);
            }

            @Override
            public R<User> selectById(Long id) {
                return R.fail(message);
            }

            @Override
            public R<User> selectByName(String name) {
                return R.fail(message);
            }

            @Override
            public R<Page<User>> list(UserDto userDto) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> checkUserValid(String username) {
                return R.fail(message);
            }
        };
    }
}