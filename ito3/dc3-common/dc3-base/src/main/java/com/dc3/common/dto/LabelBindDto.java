package com.dc3.common.dto;

import com.dc3.common.base.Converter;
import com.dc3.common.bean.Pages;
import com.dc3.common.model.LabelBind;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.beans.BeanUtils;

/**
 * LabelBind DTO
 *

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LabelBindDto extends LabelBind implements Converter<LabelBind, LabelBindDto> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pages page;

    @Override
    public void convertToDo(LabelBind bind) {
        BeanUtils.copyProperties(this, bind);
    }

    @Override
    public LabelBindDto convert(LabelBind bind) {
        BeanUtils.copyProperties(bind, this);
        return this;
    }
}