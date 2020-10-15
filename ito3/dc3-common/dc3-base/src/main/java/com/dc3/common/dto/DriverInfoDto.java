package com.dc3.common.dto;

import com.dc3.common.base.Converter;
import com.dc3.common.bean.Pages;
import com.dc3.common.model.DriverInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * DriverInfo DTO
 *

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DriverInfoDto extends DriverInfo implements Converter<DriverInfo, DriverInfoDto> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pages page;

    @Override
    public void convertToDo(DriverInfo info) {
        BeanUtils.copyProperties(this, info);
    }

    @Override
    public DriverInfoDto convert(DriverInfo info) {
        BeanUtils.copyProperties(info, this);
        return this;
    }
}