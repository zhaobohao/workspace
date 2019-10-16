package com.gitee.sop.adminserver.service.impl;

import lombok.Data;

import java.util.List;

/**
 * @author tanghc
 */
@Data
public class EurekaApplication {
    private String name;
    private List<EurekaInstance> instance;
}
