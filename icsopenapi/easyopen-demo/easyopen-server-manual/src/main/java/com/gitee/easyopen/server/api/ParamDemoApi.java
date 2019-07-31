package com.gitee.easyopen.server.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.DataType;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocField;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.easyopen.doc.annotation.ApiDocReturn;
import com.gitee.easyopen.server.api.param.GoodsParam;
import com.gitee.easyopen.server.api.param.GoodsParam3;
import com.gitee.easyopen.server.api.param.ParamDate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@ApiService
@ApiDoc("参数类型demo")
public class ParamDemoApi {

    @Api(name = "param.type.1")
    @ApiDocMethod(description = "参数类型，自定义类")
    public String demo1(GoodsParam param) {
        return JSON.toJSONString(param);
    }

    @Api(name = "param.type.2")
    @ApiDocMethod(description = "参数类型，JSONObject", params = {
            @ApiDocField(name = "id", dataType = DataType.INT, example = "1"),
            @ApiDocField(name = "goodsName", dataType = DataType.STRING, description = "商品名称", example = "iPhone6"),
    })
    public String demo2(JSONObject json) {
        return json.toJSONString();
    }

    @Api(name = "param.type.3")
    @ApiDocMethod(description = "参数类型，Map接收", params = {
            @ApiDocField(name = "id", dataType = DataType.INT, example = "1"),
            @ApiDocField(name = "goodsName", dataType = DataType.STRING, description = "商品名称", example = "iPhone6"),
    })
    public String demo3(Map<String, Object> param) {
        return JSON.toJSONString(param);
    }


    @Api(name = "param.type.4")
    @ApiDocMethod(description = "参数类型，String接收")
    @ApiDocReturn(description = "字符串返回值")
    public String demo3(
            @NotBlank(message = "不能为空")
            @Length(min = 3, max = 6, message = "长度3-6")
            @ApiDocField(description = "参数")
                    String param) {
        return param;
    }

    @Api(name = "param.type.5")
    @ApiDocMethod(description = "参数类型，int接收")
    public
    @ApiDocReturn(description = "返回值", example = "OK")
    String demo4(
            @Min(value = 1, message = "必须大于等于1")
            @ApiDocField(description = "参数i",required = true , example = "1")
                    int i) {
        return "OK";
    }

    @Api(name = "param.type.6")
    @ApiDocMethod(description = "参数类型，date")
    public
    @ApiDocReturn(description = "返回值", example = "OK")
    String demo6(ParamDate param) {
        return String.valueOf(param.getDay());
    }

    @Api(name = "param.type.7")
    @ApiDocMethod(description = "参数类型，枚举")
    public
    @ApiDocReturn(description = "返回值", example = "OK")
    String demo7(GoodsParam3 param) {
        return String.valueOf(param.getGoods_type());
    }

}
