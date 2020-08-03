package com.dc3.common.dto;

import com.dc3.common.base.Converter;
import com.dc3.common.bean.Pages;
import com.dc3.common.model.DriverAttribute;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * ConnectInfo DTO
 *
 * @author pnoker
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