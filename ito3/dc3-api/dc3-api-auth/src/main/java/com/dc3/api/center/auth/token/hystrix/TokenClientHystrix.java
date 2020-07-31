

package com.dc3.api.center.auth.token.hystrix;

import com.dc3.api.center.auth.token.feign.TokenClient;
import com.dc3.common.bean.R;
import com.dc3.common.model.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>TokenClientHystrix
 *
 * @author pnoker
 */
@Slf4j
@Component
public class TokenClientHystrix implements FallbackFactory<TokenClient> {

    @Override
    public TokenClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-AUTH" : throwable.getMessage();
        log.error("Hystrix:{}", message);

        return new TokenClient() {

            @Override
            public R<String> generateSalt(String username) {
                return R.fail(message);
            }

            @Override
            public R<String> generateToken(User user) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> checkTokenValid(String username, String token) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> cancelToken(String username) {
                return R.fail(message);
            }

        };
    }
}