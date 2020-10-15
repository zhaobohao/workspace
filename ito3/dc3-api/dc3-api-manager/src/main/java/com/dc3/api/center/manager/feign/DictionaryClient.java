package com.dc3.api.center.manager.feign;

import com.dc3.api.center.manager.hystrix.DictionaryClientHystrix;
import com.dc3.common.bean.R;
import com.dc3.common.constant.Common;
import com.dc3.common.bean.Dictionary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>字典 FeignClient
 *

 */
@FeignClient(path = Common.Service.DC3_MANAGER_DICTIONARY_URL_PREFIX, name = Common.Service.DC3_MANAGER_SERVICE_NAME, fallbackFactory = DictionaryClientHystrix.class)
public interface DictionaryClient {

    /**
     * 查询驱动 Dictionary
     *
     * @return List<Dictionary>
     */
    @GetMapping("/driver")
    R<List<Dictionary>> driverDictionary();

    /**
     * 查询驱动属性 Dictionary
     *
     * @return List<Dictionary>
     */
    @GetMapping("/driverAttribute")
    R<List<Dictionary>> driverAttributeDictionary();

    /**
     * 查询位号属性 Dictionary
     *
     * @return List<Dictionary>
     */
    @GetMapping("/pointAttribute")
    R<List<Dictionary>> pointAttributeDictionary();

    /**
     * 查询模板 Dictionary
     *
     * @return List<Dictionary>
     */
    @GetMapping("/profile")
    R<List<Dictionary>> profileDictionary();

    /**
     * 查询分组 Dictionary
     *
     * @return List<Dictionary>
     */
    @GetMapping("/group")
    R<List<Dictionary>> groupDictionary();

    /**
     * 查询设备 Dictionary
     *
     * @param parent group/driver/profile
     * @return List<Dictionary>
     */
    @GetMapping("/device/{parent}")
    R<List<Dictionary>> deviceDictionary(@NotNull @PathVariable("parent") String parent);

    /**
     * 查询位号 Dictionary
     *
     * @param parent profile/device
     * @return List<Dictionary>
     */
    @GetMapping("/point/{parent}")
    R<List<Dictionary>> pointDictionary(@NotNull @PathVariable("parent") String parent);

}
