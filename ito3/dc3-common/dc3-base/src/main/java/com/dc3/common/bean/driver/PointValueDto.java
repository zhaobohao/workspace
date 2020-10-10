

package com.dc3.common.bean.driver;

import com.dc3.common.bean.Pages;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class PointValueDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long deviceId;
    private Long pointId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pages page;

    public void convertToDo(PointValue pointValue) {
        BeanUtils.copyProperties(this, pointValue);
    }

    public PointValueDto convert(PointValue pointValue) {
        BeanUtils.copyProperties(pointValue, this);
        return this;
    }
}
