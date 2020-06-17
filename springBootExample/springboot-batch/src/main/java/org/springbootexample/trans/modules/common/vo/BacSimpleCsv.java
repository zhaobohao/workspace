package org.springbootexample.trans.modules.common.vo;

import lombok.Data;
import org.springbootexample.trans.modules.common.anno.TableName;

@TableName("simple_test")
@Data
public class BacSimpleCsv {
    private String name;
}
