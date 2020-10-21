package org.springclouddev.iot.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springclouddev.iot.common.base.Converter;
import org.springclouddev.iot.common.bean.Pages;
import org.springclouddev.iot.manager.entity.Label;
import org.springframework.beans.BeanUtils;

/**
 * Label DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LabelDto extends Label implements Converter<Label, LabelDto> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pages page;

    @Override
    public void convertToDo(Label label) {
        BeanUtils.copyProperties(this, label);
    }

    @Override
    public LabelDto convert(Label label) {
        BeanUtils.copyProperties(label, this);
        return this;
    }
}