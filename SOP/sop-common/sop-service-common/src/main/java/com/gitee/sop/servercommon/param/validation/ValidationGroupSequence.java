package com.gitee.sop.servercommon.param.validation;

import javax.validation.GroupSequence;

/**
 * @author tanghc
 */
@GroupSequence({
        // 默认的必须加上，不然没有指定groups的注解不会生效
        javax.validation.groups.Default.class,
        Group1.class,
        Group2.class,
        Group3.class,
        Group4.class,
        Group5.class,

})
public interface ValidationGroupSequence {
}

