package com.gitee.app.model;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class Goods {

    private Long id;
    @NotBlank(message = "goods_name不能为空")
    private String goods_name;
    private BigDecimal price;

}
