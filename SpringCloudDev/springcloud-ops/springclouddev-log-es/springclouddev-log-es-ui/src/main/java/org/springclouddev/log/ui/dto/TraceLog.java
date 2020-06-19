package org.springclouddev.log.ui.dto;

import java.util.List;

public class TraceLog {

    private String method;
    private String appName;
    private Long startTime;
    private Long endTime;
    private int zIndex;
    private List<TraceLog> children;


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public List<TraceLog> getChildren() {
        return children;
    }

    public void setChildren(List<TraceLog> children) {
        this.children = children;
    }
}
