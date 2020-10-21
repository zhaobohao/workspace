package org.springclouddev.iot.common.sdk.api;

import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.common.sdk.bean.CmdParameter;
import org.springclouddev.iot.common.sdk.service.DriverCommandService;
import org.springclouddev.iot.common.valid.Read;
import org.springclouddev.iot.common.valid.ValidatableList;
import org.springclouddev.iot.data.entity.PointValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 驱动操作指令 Rest Api
 *

 */
@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_DRIVER_URL_PREFIX)
public class DriverCommandApi {

    @Resource
    private DriverCommandService driverCommandService;

    /**
     * 读
     *
     * @param cmdParameters list<{deviceId,pointId}>
     * @return R<List < PointValue>>
     */
    @PostMapping("/read")
    public R<List<PointValue>> readPoint(@Validated(Read.class) @RequestBody ValidatableList<CmdParameter> cmdParameters) {
        List<PointValue> pointValues = new ArrayList<>();
        try {
            if (cmdParameters.size() > Common.Driver.MAX_REQUEST_SIZE) {
                return R.fail("point request size are limited to " + Common.Driver.MAX_REQUEST_SIZE);
            }
            cmdParameters.forEach(cmdParameter -> {
                PointValue pointValue = driverCommandService.read(cmdParameter.getDeviceId(), cmdParameter.getPointId());
                Optional.ofNullable(pointValue).ifPresent(pointValues::add);
            });
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.data(pointValues);
    }

    /**
     * 写
     *
     * @param cmdParameters list<{deviceId,pointId,stringValue}>
     * @return R<Boolean>
     */
    @PostMapping("/write")
    public R<Boolean> writePoint(@Validated(Read.class) @RequestBody ValidatableList<CmdParameter> cmdParameters) {
        try {
            if (cmdParameters.size() > Common.Driver.MAX_REQUEST_SIZE) {
                return R.fail("point request size are limited to " + Common.Driver.MAX_REQUEST_SIZE);
            }
            cmdParameters.forEach(cmdParameter -> driverCommandService.write(cmdParameter.getDeviceId(), cmdParameter.getPointId(), cmdParameter.getValue()));
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.success("ok");
    }
}
