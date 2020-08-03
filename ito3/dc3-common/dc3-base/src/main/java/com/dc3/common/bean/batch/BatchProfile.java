package com.dc3.common.bean.batch;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author pnoker
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BatchProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Boolean share;

    private Map<String, String> driverConfig;

    private List<BatchPoint> points;

    private List<BatchGroup> groups;
}
