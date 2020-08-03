package com.dc3.common.dto;

import com.dc3.common.base.Converter;
import com.dc3.common.bean.Pages;
import com.dc3.common.model.BlackIp;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.beans.BeanUtils;

/**
 * BlackIp DTO
 *
 * @author pnoker
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BlackIpDto extends BlackIp implements Converter<BlackIp, BlackIpDto> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pages page;

    @Override
    public void convertToDo(BlackIp blackIp) {
        BeanUtils.copyProperties(this, blackIp);
    }

    @Override
    public BlackIpDto convert(BlackIp blackIp) {
        BeanUtils.copyProperties(blackIp, this);
        return this;
    }
}