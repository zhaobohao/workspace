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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * @author tanghc
 */
public class ValidatorTest extends TestCase {

    private ServiceParamValidator serviceParamValidator = new ServiceParamValidator();

    /**
     * 测试JSR-303注解校验顺序，校验顺序: Group1~GroupN
     */
    public void testValidate() {
        serviceParamValidator.validateBizParam(new User("Jim", 30));
    }


    public void testField() {
        Sub sub = new Sub("sub", Type.ONE);
        Manager manager = new Manager("Jim", 22, Type.TWO, Status.OK, sub);
        Store store = new Store("仓库A", manager, Type.ONE);
        Goods goods = new Goods("Apple", new BigDecimal(50000), store);
        serviceParamValidator.validateBizParam(goods);
    }

    @Data
    @AllArgsConstructor
    private static class User {

        // 如果字段为空，无论如何都会命中这个
        @NotBlank(message = "NotBlank", groups = Group1.class)
        // 优先校验Group2
        // 可交换下面Group2,Group3，看下校验顺序
        @Length(min = 2, max = 20, message = "length must 2~20", groups = Group2.class)
        @Pattern(regexp = "[a-zA-Z]*", message = "name must letters", groups = Group3.class)
        private String name;

        @Min(value = 1, message = "min 1")
        private int age;

    }

    @Data
    @AllArgsConstructor
    static class Goods {
        @NotBlank(message = "商品名称不能为空")
        private String goodsName;

        @Min(value = 1, message = "商品价格最小值为1")
        private BigDecimal price;

        @NotNull(message = "仓库不能为空")
        private Store store;
    }

    @Data
    @AllArgsConstructor
    static class Store {
        @NotBlank(message = "库存名称不能为空")
        private String storeName;

        @NotNull(message = "管理员不能为空")
        private Manager manager;

        @NotNull(message = "Store.type不能为空")
        private Type type;
    }

    @Data
    @AllArgsConstructor
    static class Manager  {
        @NotBlank(message = "管理员姓名不能为空")
        private String name;

        private int age;

        @NotNull(message = "Manager.type不能为空")
        private Type type;

        @NotNull(message = "Manager.status不能为空")
        private Status status;

        @NotNull(message = "sub不能为空")
        private Sub sub;
    }

    @Data
    @AllArgsConstructor
    static class Sub {
        @NotBlank(message = "管理员姓名不能为空")
        private String name;

        @NotNull(message = "Sub.type不能为空")
        private Type type;
    }

    enum Type {
        ONE,TWO
    }

    enum SubType {
        OK,ERR
    }

    enum Status {
        OK, ERROR
    }

}
