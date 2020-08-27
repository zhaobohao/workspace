

package com.dc3.common.dto;

import com.dc3.common.bean.Pages;
import com.dc3.common.model.Label;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.dc3.common.base.Converter;
import lombok.*;
import org.springframework.beans.BeanUtils;

/**
 * Label DTO
 *
 * @author pnoker
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