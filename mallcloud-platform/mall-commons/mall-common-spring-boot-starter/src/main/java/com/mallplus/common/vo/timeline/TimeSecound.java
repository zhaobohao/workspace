package com.mallplus.common.vo.timeline;

import lombok.Data;

import java.io.Serializable;

@Data
public class TimeSecound implements Serializable {
    long days;//天
    long hours;  //小时
    long mins;  //分钟
    long sc;  //秒

    public TimeSecound() {
    }

    public TimeSecound(long days, long hours, long mins, long sc) {
        this.days = days;
        this.hours = hours;
        this.mins = mins;
        this.sc = sc;
    }
}
