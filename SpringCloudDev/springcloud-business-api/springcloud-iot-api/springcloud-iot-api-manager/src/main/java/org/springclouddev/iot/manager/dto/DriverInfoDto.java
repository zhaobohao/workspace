package org.springclouddev.iot.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springclouddev.iot.common.base.Converter;
import org.springclouddev.iot.common.bean.Pages;
import org.springclouddev.iot.manager.entity.DriverInfo;
import org.springframework.beans.BeanUtils;

/**
 * DriverInfo DTO
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