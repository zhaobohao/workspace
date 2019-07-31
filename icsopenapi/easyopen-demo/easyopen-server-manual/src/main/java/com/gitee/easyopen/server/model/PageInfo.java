package com.gitee.easyopen.server.model;

import java.util.List;

// 假设这是jar中的类，没法修改。但是要对其进行文档生成
public class PageInfo<T> {
    private int pageIndex;
    private int pageSize;
    private long total;

    private List<T> rows;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

}
