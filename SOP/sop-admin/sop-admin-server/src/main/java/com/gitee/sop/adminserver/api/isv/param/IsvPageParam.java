package com.gitee.sop.adminserver.api.isv.param;

import com.gitee.easyopen.doc.DataType;
import com.gitee.easyopen.doc.annotation.ApiDocBean;
import com.gitee.easyopen.doc.annotation.ApiDocField;
import com.gitee.fastmybatis.core.query.annotation.Condition;
import com.gitee.fastmybatis.core.query.param.PageParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tanghc
 */
@Getter
@Setter
@ApiDocBean(fields = {
        @ApiDocField(name = "pageIndex", description = "第几页", dataType = DataType.INT, example = "1"),
        @ApiDocField(name = "pageSize", description = "每页几条数据", dataType = DataType.INT, example = "10"),
})
public class IsvPageParam extends PageParam {
    @ApiDocField(name = "appKey", description = "appKey", dataType = DataType.STRING, example = "111111")
    @Condition(ignoreEmptyString = true)
    private String appKey;
}
