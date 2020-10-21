package org.springclouddev.iot.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springclouddev.iot.common.base.Converter;
import org.springclouddev.iot.common.bean.Pages;
import org.springclouddev.iot.manager.entity.Driver;
import org.springframework.beans.BeanUtils;

/**
 * Driver DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DriverDto extends Driver implements Converter<Driver, DriverDto> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pages page;

    @Override
    public void convertToDo(Driver driver) {
        BeanUtils.copyProperties(this, driver);
    }

    @Override
    public DriverDto convert(Driver driver) {
        BeanUtils.copyProperties(driver, this);
        return this;
    }
}