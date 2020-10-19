package org.springclouddev.iot.manager.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BatchProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Boolean share;

    private Map<String, String> driverConfig;

    private List<BatchPoint> points;
    /**
     * 仅当share为true的时候生效
     */
    private Map<String, Map<String, String>> pointConfig;

    private List<BatchGroup> groups;
}
