package org.springclouddev.iot.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springclouddev.iot.common.base.Converter;
import org.springclouddev.iot.common.bean.Pages;
import org.springclouddev.iot.manager.entity.Device;
import org.springframework.beans.BeanUtils;

/**
 * Device DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DeviceDto extends Device implements Converter<Device, DeviceDto> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pages page;

    @Override
    public void convertToDo(Device device) {
        BeanUtils.copyProperties(this, device);
    }

    @Override
    public DeviceDto convert(Device device) {
        BeanUtils.copyProperties(device, this);
        return this;
    }
}