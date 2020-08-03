package com.dc3.common.dto;

import com.dc3.common.bean.Pages;
import com.dc3.common.model.PointAttribute;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.dc3.common.base.Converter;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * PointAttribute DTO
 *
 * @author pnoker
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PointAttributeDto extends PointAttribute implements Converter<PointAttribute, PointAttributeDto> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pages page;

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