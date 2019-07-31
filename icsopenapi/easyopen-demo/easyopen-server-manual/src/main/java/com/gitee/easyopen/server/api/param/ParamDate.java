package com.gitee.easyopen.server.api.param;

import com.gitee.easyopen.doc.DataType;
import com.gitee.easyopen.doc.annotation.ApiDocField;

import java.util.Date;

/**
 * @author tanghc
 */
public class ParamDate {
    @ApiDocField(description = "日期",dataType = DataType.DATETIME)
    private Date day;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }
}
