package com.gitee.sop.storyweb.controller;

import com.gitee.sop.storyweb.controller.param.GoodsUpdateParam;
import com.gitee.sop.storyweb.message.GoodsErrorEnum;
import com.gitee.sop.servercommon.annotation.ApiMapping;
import com.gitee.sop.servercommon.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * 演示如何抛出异常
 * @author tanghc
 */
@RestController
public class ThrowExceptionDemoController {

    @ApiMapping(value = "goods.update")
    public Object updateGoods(GoodsUpdateParam param) {
        // 方式1
        if ("iphone6".equals(param.getGoods_name())) {
            throw new ServiceException("不能更新iphone6");
        }
        // 方式2，国际化
        if (StringUtils.isEmpty(param.getGoods_name())) {
            throw GoodsErrorEnum.NO_GOODS_NAME.getErrorMeta().getException();
        }
        // 方式3，国际化传参
        if (param.getGoods_name().length() <= 3) {
            throw GoodsErrorEnum.LESS_GOODS_NAME_LEN.getErrorMeta().getException(3);
        }
        return param;
    }
}
