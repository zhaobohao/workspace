package com.gitee.easyopen.template;

import java.util.List;

/**
 * @author tanghc
 */
public class StatusParam {
    private List<String> nameVersionList;
    private Byte status;

    public List<String> getNameVersionList() {
        return nameVersionList;
    }

    public void setNameVersionList(List<String> nameVersionList) {
        this.nameVersionList = nameVersionList;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}