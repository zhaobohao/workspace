package org.springclouddev.iot.manager.feign;

import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.bean.Dictionary;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Component
public class DictionaryClientFallback implements DictionaryClient {

    String message = "No available server for client: DC3-MANAGER";

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
}