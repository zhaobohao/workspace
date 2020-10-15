package com.dc3.driver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DevicePayLoad {
    private Long deviceId;
    private Long pointId;
    private String value;
}
