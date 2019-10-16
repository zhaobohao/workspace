package com.gitee.sop.servercommon;

import com.gitee.sop.servercommon.param.ServiceParamValidator;
import com.gitee.sop.servercommon.param.validation.Group1;
import com.gitee.sop.servercommon.param.validation.Group2;
import com.gitee.sop.servercommon.param.validation.Group3;
import junit.framework.TestCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author tanghc
 */
public class ValidatorTest extends TestCase {

    private ServiceParamValidator serviceParamValidator = new ServiceParamValidator();

    /**
     * 测试JSR-303注解校验顺序，校验顺序: Group1~GroupN
     */
    public void testValidate() {
        serviceParamValidator.validateBizParam(new User("1", 1));
    }


    @Data
    @AllArgsConstructor
    private static class User {

        // 如果字段为空，无论如何都会命中这个
        @NotBlank(message = "NotBlank", groups = Group1.class)
        // 优先校验Group2
        // 可交换下面Group2,Group3，看下校验顺序
        @Length(min = 2, max = 20, message = "length must 10~20", groups = Group2.class)
        @Pattern(regexp = "[a-zA-Z]*", message = "name must letters", groups = Group3.class)
        private String name;

        @Min(value = 1, message = "min 1")
        private int age;

    }

}
