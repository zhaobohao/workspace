package org.springclouddev.iot.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springclouddev.iot.common.base.Converter;
import org.springclouddev.iot.common.bean.Pages;
import org.springclouddev.iot.manager.entity.Point;
import org.springframework.beans.BeanUtils;

/**
 * Point DTO
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