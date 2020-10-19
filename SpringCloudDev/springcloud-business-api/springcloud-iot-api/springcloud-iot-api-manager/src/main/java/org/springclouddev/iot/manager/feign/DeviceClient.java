package org.springclouddev.iot.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.common.valid.Insert;
import org.springclouddev.iot.common.valid.Update;
import org.springclouddev.iot.manager.dto.DeviceDto;
import org.springclouddev.iot.manager.entity.Device;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 设备 FeignClient
 *

 */
@FeignClient(path = Common.Service.DC3_MANAGER_DEVICE_URL_PREFIX, name = Common.Service.DC3_MANAGER_SERVICE_NAME, fallbackFactory = DeviceClientFallback.class)
public interface DeviceClient {

    /**
     * 新增 Device
     *
     * @param device Device
     * @return R<Device>
     */
    @PostMapping("/add")
    R<Device> add(@Validated(Insert.class) @RequestBody Device device);

    /**
     * 根据 ID 删除 Device
     *
     * @param id Device Id
     * @return R<Boolean>
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 修改 Device
     *
     * @param device Device
     * @return R<Device>
     */
    @PostMapping("/update")
    R<Device> update(@Validated(Update.class) @RequestBody Device device);

    /**
     * 根据 ID 查询 Device
     *
     * @param id Device Id
     * @return R<Device>
     */
    @GetMapping("/id/{id}")
    R<Device> selectById(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 查询 Device 服务状态
     * ONLINE, OFFLINE, MAINTAIN, FAULT
     *
     * @param deviceDto Device Dto
     * @return Map<Long, String>
     */
    @PostMapping("/status")
    R<Map<Long, String>> deviceStatus(@RequestBody(required = false) DeviceDto deviceDto);

    /**
     * 分页查询 Device
     *
     * @param deviceDto Device Dto
     * @return R<Page < Device>>
     */
    @PostMapping("/list")
    R<Page<Device>> list(@RequestBody(required = false) DeviceDto deviceDto);

}
