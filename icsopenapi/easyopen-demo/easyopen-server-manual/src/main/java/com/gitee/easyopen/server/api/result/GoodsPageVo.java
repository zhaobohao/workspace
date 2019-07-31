package com.gitee.easyopen.server.api.result;

import com.gitee.easyopen.doc.DataType;
import com.gitee.easyopen.doc.annotation.ApiDocBean;
import com.gitee.easyopen.doc.annotation.ApiDocField;
import com.gitee.easyopen.server.model.PageInfo;

@ApiDocBean(fields = {
    @ApiDocField(name="pageIndex",description="第几页",dataType=DataType.INT,example="1"),
    @ApiDocField(name="pageSize",description="每页几条数据",dataType=DataType.INT,example="10"),
    @ApiDocField(name="total",description="每页几条数据",dataType=DataType.LONG,example="100"),
    @ApiDocField(name="rows",description="商品列表",dataType=DataType.ARRAY,elementClass=Goods.class),
})
public class GoodsPageVo extends PageInfo<Goods> {

}
