package org.springclouddev.iot.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springclouddev.iot.common.base.Converter;
import org.springclouddev.iot.common.bean.Pages;
import org.springclouddev.iot.manager.entity.DriverAttribute;
import org.springframework.beans.BeanUtils;

/**
 * ConnectInfo DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DriverAttributeDto extends DriverAttribute implements Converter<DriverAttribute, DriverAttributeDto> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pages page;

    @Override
    public void convertToDo(DriverAttribute info) {
        BeanUtils.copyProperties(this, info);
    }

    @Override
    public DriverAttributeDto convert(DriverAttribute info) {
        BeanUtils.copyProperties(info, this);
        return this;
    }
}