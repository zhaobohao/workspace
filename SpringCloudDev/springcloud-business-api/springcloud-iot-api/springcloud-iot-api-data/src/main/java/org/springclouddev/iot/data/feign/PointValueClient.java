package org.springclouddev.iot.data.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.data.dto.PointValueDto;
import org.springclouddev.iot.data.entityintValue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 数据 FeignClient
 *

 */
@FeignClient(path = Common.Service.DC3_DATA_URL_PREFIX, name = Common.Service.DC3_DATA_SERVICE_NAME, fallbackFactory = PointValueClientFallback.class)
public interface PointValueClient {

    /**
     * 获取设备状态
     * ONLINE, OFFLINE, MAINTAIN, FAULT
     *
     * @param deviceId Device Id
     * @return String
     */
    @GetMapping("/status/deviceId/{deviceId}")
    R<String> status(@NotNull @PathVariable(value = "deviceId") Long deviceId);

    /**
     * 获取实时值，读 Redis
     *
     * @param deviceId Device Id
     * @return String Value
     */
    @GetMapping("/realtime/deviceId/{deviceId}")
    R<List<PointValue>> realtime(@NotNull @PathVariable(value = "deviceId") Long deviceId);

    /**
     * 获取实时值，读 Redis
     *
     * @param deviceId Device Id
     * @param pointId  Point Id
     * @return String Value
     */
    @GetMapping("/realtime/deviceId/{deviceId}/pointId/{pointId}")
    R<PointValue> realtime(@NotNull @PathVariable(value = "deviceId") Long deviceId, @NotNull @PathVariable(value = "pointId") Long pointId);

    /**
     * 查询最新 PointValue
     *
     * @param deviceId Device Id
     * @return PointValue
     */
    @GetMapping("/latest/deviceId/{deviceId}")
    R<PointValue> latest(@NotNull @PathVariable(value = "deviceId") Long deviceId);

    /**
     * 查询最新 PointValue
     *
     * @param deviceId Device Id
     * @param pointId  Point Id
     * @return PointValue
     */
    @GetMapping("/latest/deviceId/{deviceId}/pointId/{pointId}")
    R<PointValue> latest(@NotNull @PathVariable(value = "deviceId") Long deviceId, @NotNull @PathVariable(value = "pointId") Long pointId);

    /**
     * 分页查询 PointValue
     *
     * @param pointValueDto PointValueDto
     * @return Page<PointValue>
     */
    @PostMapping("/list")
    R<IPage<PointValue>> list(@RequestBody(required = false) PointValueDto pointValueDto);
}
