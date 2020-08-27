

package com.dc3.common.dto;

import com.dc3.common.base.Converter;
import com.dc3.common.bean.Pages;
import com.dc3.common.model.Rtmp;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * Rtmp DTO
 *
 * @author pnoker
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class RtmpDto implements Serializable, Converter<Rtmp, RtmpDto> {
    private static final long serialVersionUID = 1L;

    private String name;
    private Short videoType;
    private Boolean run;
    private Boolean autoStart;
    private Long userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pages page;

    public RtmpDto(boolean autoStart) {
        this.autoStart = autoStart;
    }

    @Override
    public void convertToDo(Rtmp rtmp) {
        BeanUtils.copyProperties(this, rtmp);
    }

    @Override
    public RtmpDto convert(Rtmp rtmp) {
        BeanUtils.copyProperties(rtmp, this);
        return this;
    }
}