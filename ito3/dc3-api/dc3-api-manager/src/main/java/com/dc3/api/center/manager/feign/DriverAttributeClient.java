package com.dc3.api.center.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.hystrix.DriverAttributeClientHystrix;
import com.dc3.common.bean.R;
import com.dc3.common.constant.Common;
import com.dc3.common.dto.DriverAttributeDto;
import com.dc3.common.model.DriverAttribute;
import com.dc3.common.valid.Insert;
import com.dc3.common.valid.Update;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

/**
 * <p>驱动配置属性 FeignClient
 *
 * @author pnoker
 */
@FeignClient(path = Common.Service.DC3_MANAGER_DRIVER_ATTRIBUTE_URL_PREFIX, name = Common.Service.DC3_MANAGER_SERVICE_NAME, fallbackFactory = DriverAttributeClientHystrix.class)
public interface DriverAttributeClient {

    /**
     * 新增 DriverAttribute
     *
     * @param driverAttribute DriverAttribute
     * @return DriverAttribute
     */
    @PostMapping("/add")
    R<DriverAttribute> add(@Validated(Insert.class) @RequestBody DriverAttribute driverAttribute);

    /**
     * 根据 ID 删除 DriverAttribute
     *
     * @param id DriverAttributeId
     * @return Boolean
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 修改 DriverAttribute
     *
     * @param driverAttribute DriverAttribute
     * @return DriverAttribute
     */
    @PostMapping("/update")
    R<DriverAttribute> update(@Validated(Update.class) @RequestBody DriverAttribute driverAttribute);

    /**
     * 根据 ID 查询 DriverAttribute
     *
     * @param id DriverAttribute Id
     * @return DriverAttribute
     */
    @GetMapping("/id/{id}")
    R<DriverAttribute> selectById(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 分页查询 DriverAttribute
     *
     * @param driverAttributeDto DriverAttribute Dto
     * @return Page<DriverAttribute>
     */
    @PostMapping("/list")
    R<Page<DriverAttribute>> list(@RequestBody(required = false) DriverAttributeDto driverAttributeDto);

}
