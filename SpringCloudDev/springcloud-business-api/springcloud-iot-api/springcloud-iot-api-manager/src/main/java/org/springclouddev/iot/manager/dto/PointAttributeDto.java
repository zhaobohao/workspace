package org.springclouddev.iot.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springclouddev.iot.common.base.Converter;
import org.springclouddev.iot.manager.entity.PointAttribute;
import org.springframework.beans.BeanUtils;

/**
 * PointAttribute DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PointAttributeDto extends PointAttribute implements Converter<PointAttribute, PointAttributeDto> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private com.dc3.common.bean.Pages page;

    @Override
    public void convertToDo(PointAttribute info) {
        BeanUtils.copyProperties(this, info);
    }

    @Override
    public PointAttributeDto convert(PointAttribute info) {
        BeanUtils.copyProperties(info, this);
        return this;
    }
}