package org.springclouddev.iot.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.common.valid.Insert;
import org.springclouddev.iot.common.valid.Update;
import org.springclouddev.iot.manager.dto.DriverDto;
import org.springclouddev.iot.manager.entity.Driver;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * <p>驱动 FeignClient
 *

 */
@FeignClient(path = Common.Service.DC3_MANAGER_DRIVER_URL_PREFIX, name = Common.Service.DC3_MANAGER_SERVICE_NAME, fallbackFactory = DriverClientFallback.class)
public interface DriverClient {

    /**
     * 新增 Driver
     *
     * @param driver Driver
     * @return Driver
     */
    @PostMapping("/add")
    R<Driver> add(@Validated(Insert.class) @RequestBody Driver driver);

    /**
     * 根据 ID 删除 Driver
     *
     * @param id Driver Id
     * @return Boolean
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 修改 Driver
     *
     * @param driver Driver
     * @return Driver
     */
    @PostMapping("/update")
    R<Driver> update(@Validated(Update.class) @RequestBody Driver driver);

    /**
     * 根据 ID 查询 Driver
     *
     * @param id Driver Id
     * @return Driver
     */
    @GetMapping("/id/{id}")
    R<Driver> selectById(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 根据 SERVICENAME 查询 Driver
     *
     * @param serviceName Driver Service Name
     * @return Driver
     */
    @GetMapping("/service/{serviceName}")
    R<Driver> selectByServiceName(@NotNull @PathVariable(value = "serviceName") String serviceName);

    /**
     * 根据 HOST & PORT 查询 Driver
     *
     * @param host Driver Host
     * @param port Driver Port
     * @return Driver
     */
    @GetMapping("/host/{host}/port/{port}")
    R<Driver> selectByHostPort(@NotNull @PathVariable(value = "host") String host, @NotNull @PathVariable(value = "port") Integer port);

    /**
     * 查询 Driver 服务状态
     * ONLINE, OFFLINE
     *
     * @param driverDto Driver Dto
     * @return Map<String, Boolean>
     */
    @PostMapping("/status")
    R<Map<String, Boolean>> driverStatus(@RequestBody(required = false) DriverDto driverDto);

    /**
     * 分页查询 Driver
     *
     * @param driverDto Driver Dto
     * @return Page<Driver>
     */
    @PostMapping("/list")
    R<Page<Driver>> list(@RequestBody(required = false) DriverDto driverDto);

}
