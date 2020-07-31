

package com.dc3.api.center.manager.hystrix;

import com.dc3.api.center.manager.feign.DictionaryClient;
import com.dc3.common.bean.R;
import com.dc3.common.bean.Dictionary;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>DictionaryClientHystrix
 *
 * @author pnoker
 */
@Slf4j
@Component
public class DictionaryClientHystrix implements FallbackFactory<DictionaryClient> {

    @Override
    public DictionaryClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-MANAGER" : throwable.getMessage();
        log.error("Hystrix:{}", message);

        return new DictionaryClient() {

            @Override
            public R<List<Dictionary>> driverDictionary() {
                return R.fail(message);
            }

            @Override
            public R<List<Dictionary>> driverAttributeDictionary() {
                return R.fail(message);
            }

            @Override
            public R<List<Dictionary>> pointAttributeDictionary() {
                return R.fail(message);
            }

            @Override
            public R<List<Dictionary>> profileDictionary() {
                return R.fail(message);
            }

            @Override
            public R<List<Dictionary>> groupDictionary() {
                return R.fail(message);
            }

            @Override
            public R<List<Dictionary>> deviceDictionary(@NotNull String parent) {
                return R.fail(message);
            }

            @Override
            public R<List<Dictionary>> pointDictionary(@NotNull String parent) {
                return R.fail(message);
            }
        };
    }
}