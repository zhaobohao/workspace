package org.springclouddev.iot.manager.api;

import org.springclouddev.iot.manager.feign.DictionaryClient;
import org.springclouddev.iot.manager.service.DictionaryService;
import org.springclouddev.iot.common.bean.Dictionary;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_MANAGER_DICTIONARY_URL_PREFIX)
public class DictionaryApi implements DictionaryClient {
    @Resource
    private DictionaryService dictionaryService;

    @Override
    public R<List<Dictionary>> driverDictionary() {
        try {
            List<Dictionary> dictionaryList = dictionaryService.driverDictionary();
            if (null != dictionaryList) {
                return R.data(dictionaryList);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<List<Dictionary>> driverAttributeDictionary() {
        try {
            List<Dictionary> dictionaryList = dictionaryService.driverAttributeDictionary();
            if (null != dictionaryList) {
                return R.data(dictionaryList);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<List<Dictionary>> pointAttributeDictionary() {
        try {
            List<Dictionary> dictionaryList = dictionaryService.pointAttributeDictionary();
            if (null != dictionaryList) {
                return R.data(dictionaryList);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<List<Dictionary>> profileDictionary() {
        try {
            List<Dictionary> dictionaryList = dictionaryService.profileDictionary();
            if (null != dictionaryList) {
                return R.data(dictionaryList);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<List<Dictionary>> groupDictionary() {
        try {
            List<Dictionary> dictionaryList = dictionaryService.groupDictionary();
            if (null != dictionaryList) {
                return R.data(dictionaryList);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<List<Dictionary>> deviceDictionary(@NotNull String parent) {
        try {
            List<Dictionary> dictionaryList = dictionaryService.deviceDictionary(parent);
            if (null != dictionaryList) {
                return R.data(dictionaryList);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<List<Dictionary>> pointDictionary(@NotNull String parent) {
        try {
            List<Dictionary> dictionaryList = dictionaryService.pointDictionary(parent);
            if (null != dictionaryList) {
                return R.data(dictionaryList);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }
}
