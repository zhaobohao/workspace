package com.gitee.easyopen.server.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.easyopen.Result;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.DataType;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocBean;
import com.gitee.easyopen.doc.annotation.ApiDocField;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.easyopen.doc.annotation.ApiDocRootData;
import com.gitee.easyopen.server.api.param.DeleteParam;
import com.gitee.easyopen.server.api.param.GoodsParam;
import com.gitee.easyopen.server.api.result.Goods;
import com.gitee.easyopen.server.model.PageInfo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * 文档定义demo
 * @author tanghc 
 */
@ApiService
@ApiDoc(value = "文档demo，参考DocDemoApi.java", order = 2)
public class DocDemoApi {

    static Goods goods = new Goods();
    static {
        goods.setId(1L);
        goods.setGoods_name("苹果iPhoneX");
        goods.setPrice(new BigDecimal(8000));
    }

    // 参数方式1，参数类GoodsParam@ApiDocField
    @Api(name = "doc.param.1")
    @ApiDocMethod(description = "参数方式1,默认", remark = "参数类GoodsParam@ApiDocField")
    public Goods demo1(GoodsParam param) {
        return goods;
    }

    // 参数方式2，指定参数类Demo2Param，Demo2Param继承GoodsParam内容
    @Api(name = "doc.param.2")
    @ApiDocMethod(description = "参数方式2,继承", paramClass = Demo2Param.class, remark = "指定参数类Demo2Param，Demo2Param继承GoodsParam内容")
    public Goods demo2(GoodsParam param) {
        return goods;
    }

    // 参数方式3，参数里面有参数，Demo3Param内聚合GoodsParam
    @Api(name = "doc.param.3")
    @ApiDocMethod(description = "参数方式3,聚合", remark = "参数里面有参数，Demo3Param内聚合GoodsParam")
    public Goods demo3(Demo3Param param) {
        return goods;
    }

    // 参数方式4，自定义属性
    @Api(name = "doc.param.4")
    @ApiDocMethod(description = "参数方式4,自定义属性，第一个", params = { 
            @ApiDocField(name = "id", dataType = DataType.INT),
            @ApiDocField(name = "goods_name", dataType = DataType.STRING, description = "商品名称"),
    }, remark = "自定义属性", order = 1 // 指定了order，优先按这个值排序
    )
    public Goods param4(GoodsParam param) {
        return goods;
    }
    
    // 参数方式5，外部类指定参数，可复用
    @Api(name = "doc.param.5")
    @ApiDocMethod(description = "参数方式5，外部类指定参数，可复用，第二个", paramClass = DemoTopParam.class, remark = "参数方式5，外部类指定参数，可复用", order = 2)
    public Goods param5(JSONObject param) {
        return goods;
    }
    
    // 参数方式6，数组参数
    @Api(name = "doc.param.6")
    @ApiDocMethod(description = "参数方式6，数组参数")
    public void param6(DeleteParam param) {
        System.out.println(param.getIds());
    }

    // 参数方式7，数组参数
    @Api(name = "doc.param.7")
    @ApiDocMethod(description = "参数方式7，数组对象参数")
    public void param7(DemoArrObjParam param) {
        System.out.println(JSON.toJSONString(param));
    }
    //////////////////////////////////////////////////////////////
    // 返回结果0，没有返回结果
    @Api(name = "doc.result.0")
    @ApiDocMethod(description = "返回结果0,没有返回结果")
    public void result0(GoodsParam param){
    }

    // 返回结果1，默认
    @Api(name = "doc.result.1")
    @ApiDocMethod(description = "返回结果1,默认")
    public Goods result1(GoodsParam param) {
        return goods;
    }

    // 返回结果2，使用指定的返回结果类
    @Api(name = "doc.result.2")
    @ApiDocMethod(description = "返回结果2,指定返回结果类", resultClass = Result1.class, remark = "使用指定的返回结果类")
    public Goods result2(GoodsParam param) {
        return goods;
    }

    // 返回结果3，自定义返回字段
    @Api(name = "doc.result.3")
    @ApiDocMethod(description = "返回结果3,自定义字段", results = { 
            @ApiDocField(name = "id", description = "id"),
            @ApiDocField(name = "remark", description = "备注") 
    }, remark = "自定义返回字段")
    public Goods result3(GoodsParam param) {
        return goods;
    }
    
    // 返回结果4
    @Api(name = "doc.result.4")
    @ApiDocMethod(description = "返回结果4,返回List", elementClass=Goods.class, remark = "自定义返回字段")
    public List<Goods> result4(GoodsParam param) {
        return Arrays.asList(goods);
    }
    
    // 返回结果5
    @Api(name = "doc.result.5")
    @ApiDocMethod(description="返回结果5,外部指定"
            ,results={@ApiDocField(name="pageIndex",description="第几页",dataType=DataType.INT,example="1"),
                    @ApiDocField(name="pageSize",description="每页几条数据",dataType=DataType.INT,example="10"),
                    @ApiDocField(name="total",description="每页几条数据",dataType=DataType.LONG,example="100"),
                    @ApiDocField(name="rows",description="数据",dataType=DataType.ARRAY,elementClass=Goods.class),}
            )
    // 假设PageInfo是jar中的类，没法修改。但是要对其进行文档生成
    public PageInfo<Goods> pageinfo(GoodsParam param) {
        
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setGoods_name("苹果iPhone5");
        goods.setPrice(new BigDecimal(3000));
        
        Goods goods2 = new Goods();
        goods2.setId(2L);
        goods2.setGoods_name("苹果iPhone6");
        goods2.setPrice(new BigDecimal(8000));
        List<Goods> list = Arrays.asList(goods, goods2);
        
        PageInfo<Goods> pageInfo = new PageInfo<>();
        pageInfo.setPageIndex(1);
        pageInfo.setPageSize(10);
        pageInfo.setTotal(100);
        pageInfo.setRows(list);
        
        return pageInfo;
    }
    
    // 返回结果6，自定义返回字段,包含自定义类
    @Api(name = "doc.result.6")
    @ApiDocMethod(description = "返回结果6,自定义类", results = { 
            @ApiDocField(name = "id", description = "id"),
            @ApiDocField(name = "remark", description = "备注"),
            @ApiDocField(name = "result1", description = "result1",beanClass=Result1.class), // 自定义类
    }, remark = "自定义返回字段,包含自定义类")
    public Goods result6(GoodsParam param) {
        return goods;
    }
    
    @Api(name = "doc.result.7")
    @ApiDocMethod(description="返回结果7,模板复用",resultClass = GoodsVo.class)
    public Goods result7(GoodsParam param) {
        return goods;
    }

    @Api(name = "doc.result.8")
    @ApiDocMethod(description="返回结果8,最外部包装类", wrapperClass = MyResult.class)
    public Goods result8(GoodsParam param) {
        return goods;
    }

    static class StringParam {
        @ApiDocField(description = "内容")
        private String text;

        public String getText() {
            return text;
        }
        public void setText(String text) {
            this.text = text;
        }
    }

    @Api(name = "doc.result.9")
    @ApiDocMethod(description="返回结果9")
    public StringParam result9(StringParam param) {
        return new StringParam();
    }

    public static class MyResult implements Result {
        @ApiDocField(description = "子错误码")
        private String subCode;
        @ApiDocField(description = "子错误信息")
        private String subMSg;
        @ApiDocField(description = "错误码")
        private String code;
        @ApiDocField(description = "数据类型")
        @ApiDocRootData
        private Object data;

        @Override
        public void setCode(Object code) {

        }

        @Override
        public void setMsg(String msg) {

        }

        @Override
        public void setData(Object data) {
            this.data = data;
        }

        public String getSubCode() {
            return subCode;
        }

        public void setSubCode(String subCode) {
            this.subCode = subCode;
        }

        public String getSubMSg() {
            return subMSg;
        }

        public void setSubMSg(String subMSg) {
            this.subMSg = subMSg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Object getData() {
            return data;
        }
    }

    //////////////////////////////
    
    // 参数模板类，可复用
    @ApiDocBean(fields = {
        @ApiDocField(name = "id", description = "id"),
        @ApiDocField(name="pageIndex",description="第几页",dataType=DataType.INT,example="1"),
        @ApiDocField(name="pageSize",description="每页几条数据",dataType=DataType.INT,example="10"),
    })
    public static class DemoTopParam {
    }

    public static class Demo2Param extends GoodsParam { // 继承
        @ApiDocField(description = "id列表", elementClass = Integer.class)
        private List<Integer> idList;

        @ApiDocField(description = "时间")
        private Timestamp time;

        public List<Integer> getIdList() {
            return idList;
        }

        public void setIdList(List<Integer> idList) {
            this.idList = idList;
        }
    }

    public static class DemoArrObjParam {
        @ApiDocField(description = "id")
        private Long id;

        @ApiDocField(description = "商品数组", elementClass = Demo33Param.class)
        private List<Demo33Param> list;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public List<Demo33Param> getList() {
            return list;
        }

        public void setList(List<Demo33Param> list) {
            this.list = list;
        }
    }


    public static class Demo3Param {
        @ApiDocField(description = "id", required = true, example = "1")
        private int id;
        @ApiDocField(description = "GoodsParam", required = true)
        private Demo33Param param; // 另外一个参数

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Demo33Param getParam() {
            return param;
        }

        public void setParam(Demo33Param param) {
            this.param = param;
        }
    }
    
    public static class Demo33Param {
        @ApiDocField(description = "id2", required = true, example = "12")
        private int id2;
        @ApiDocField(description = "GoodsParam", required = true)
        private Demo333Param param33; // 另外一个参数
        @ApiDocField(description = "price", required = true, example = "1")
        private long price;

        public int getId2() {
            return id2;
        }

        public void setId2(int id2) {
            this.id2 = id2;
        }

        public Demo333Param getParam33() {
            return param33;
        }

        public void setParam33(Demo333Param param33) {
            this.param33 = param33;
        }

        public long getPrice() {
            return price;
        }

        public void setPrice(long price) {
            this.price = price;
        }
    }
    
    public static class Demo333Param {
        @ApiDocField(description = "id", required = true, example = "1")
        private int id3;
        @ApiDocField(description = "GoodsParam", required = true)
        private GoodsParam param1; // 另外一个参数

        public int getId3() {
            return id3;
        }

        public void setId3(int id3) {
            this.id3 = id3;
        }

        public GoodsParam getParam1() {
            return param1;
        }

        public void setParam1(GoodsParam param1) {
            this.param1 = param1;
        }
    }

    public static class Result1 extends Goods {
        @ApiDocField(description = "备注")
        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
    
    // 参数模板
    @ApiDocBean(fields = {
        @ApiDocField(name="pageIndex",description="第几页",dataType=DataType.INT,example="1"),
        @ApiDocField(name="pageSize",description="每页几条数据",dataType=DataType.INT,example="10"),
        @ApiDocField(name="total",description="每页几条数据",dataType=DataType.LONG,example="100"),
        @ApiDocField(name="rows",description="商品列表",dataType=DataType.ARRAY,elementClass=Goods.class),
    })
    public static class GoodsVo extends PageInfo<Goods> {
    }


}
