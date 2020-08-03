package com.dc3.common.dto;

import com.dc3.common.bean.Pages;
import com.dc3.common.model.Point;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.dc3.common.base.Converter;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * Point DTO
 *
 * @author pnoker
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PointDto extends Point implements Converter<Point, PointDto> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pages page;

    @Override
    public void convertToDo(Point point) {
        BeanUtils.copyProperties(this, point);
    }

    @Override
    public PointDto convert(Point point) {
        BeanUtils.copyProperties(point, this);
        return this;
    }
}