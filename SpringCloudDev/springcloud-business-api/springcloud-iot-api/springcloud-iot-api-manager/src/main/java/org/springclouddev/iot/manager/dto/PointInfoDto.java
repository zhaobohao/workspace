package org.springclouddev.iot.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springclouddev.iot.common.base.Converter;
import org.springclouddev.iot.manager.entity.PointInfo;
import org.springframework.beans.BeanUtils;

/**
 * PointInfo DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PointInfoDto extends PointInfo implements Converter<PointInfo, PointInfoDto> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private com.dc3.common.bean.Pages page;

    @Override
    public void convertToDo(PointInfo info) {
        BeanUtils.copyProperties(this, info);
    }

    @Override
    public PointInfoDto convert(PointInfo info) {
        BeanUtils.copyProperties(info, this);
        return this;
    }
}