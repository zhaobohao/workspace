package org.springclouddev.iot.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.common.valid.Insert;
import org.springclouddev.iot.common.valid.Update;
import org.springclouddev.iot.manager.dto.DriverInfoDto;
import org.springclouddev.iot.manager.entity.DriverInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

/**
 * <p>驱动配置信息 FeignClient
 *

 */
@FeignClient(path = Common.Service.DC3_MANAGER_DRIVER_INFO_URL_PREFIX, name = Common.Service.DC3_MANAGER_SERVICE_NAME, fallbackFactory = DriverInfoClientFallback.class)
public interface DriverInfoClient {

    /**
     * 新增 DriverInfo
     *
     * @param driverInfo DriverInfo
     * @return DriverInfo
     */
    @PostMapping("/add")
    R<DriverInfo> add(@Validated(Insert.class) @RequestBody DriverInfo driverInfo);

    /**
     * 根据 ID 删除 DriverInfo
     *
     * @param id DriverInfo Id
     * @return Boolean
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 修改 DriverInfo
     *
     * @param driverInfo DriverInfo
     * @return DriverInfo
     */
    @PostMapping("/update")
    R<DriverInfo> update(@Validated(Update.class) @RequestBody DriverInfo driverInfo);

    /**
     * 根据 ID 查询 DriverInfo
     *
     * @param id DriverInfo Id
     * @return DriverInfo
     */
    @GetMapping("/id/{id}")
    R<DriverInfo> selectById(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 分页查询 DriverInfo
     *
     * @param driverInfoDto DriverInfo Dto
     * @return Page<DriverInfo>
     */
    @PostMapping("/list")
    R<Page<DriverInfo>> list(@RequestBody(required = false) DriverInfoDto driverInfoDto);

}
