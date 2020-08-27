

package com.dc3.common.bean.driver;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author pnoker
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeviceStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long deviceId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;

    private int timeOut = 15;
    private TimeUnit timeUnit = TimeUnit.MINUTES;

    private Long originTime;

    public DeviceStatus(Long deviceId, String status) {
        this.deviceId = deviceId;
        this.status = status;
        this.originTime = System.currentTimeMillis();
    }
}
