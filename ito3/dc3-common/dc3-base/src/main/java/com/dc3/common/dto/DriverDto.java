package com.dc3.common.dto;

import com.dc3.common.base.Converter;
import com.dc3.common.bean.Pages;
import com.dc3.common.model.Driver;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.beans.BeanUtils;

/**
 * Driver DTO
 *
 * @author pnoker
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