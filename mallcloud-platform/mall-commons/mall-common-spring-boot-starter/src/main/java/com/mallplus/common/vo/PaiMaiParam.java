package com.mallplus.common.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 创建和修改商品时使用的参数
 * https://github.com/shenzhuan/mallplus on 2018/4/26.
 */
@Data
public class PaiMaiParam implements Serializable {
    private Long id;
    private BigDecimal price;
}
