package org.springclouddev.iot.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springclouddev.iot.common.base.Converter;
import org.springclouddev.iot.manager.entity.LabelBind;
import org.springframework.beans.BeanUtils;

/**
 * LabelBind DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LabelBindDto extends LabelBind implements Converter<LabelBind, LabelBindDto> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private com.dc3.common.bean.Pages page;

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