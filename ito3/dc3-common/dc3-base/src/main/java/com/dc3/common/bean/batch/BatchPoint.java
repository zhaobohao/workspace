

package com.dc3.common.bean.batch;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author pnoker
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BatchPoint implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String type;
    private Short rw;
    private Float base;
    private Float minimum;
    private Float maximum;
    private Float multiple;
    private Boolean accrue;
    private String format;
    private String unit;
}
