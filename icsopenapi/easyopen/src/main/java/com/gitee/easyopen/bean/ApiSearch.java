package com.gitee.easyopen.bean;

import java.io.Serializable;

/**
 * @author tanghc
 */
public class ApiSearch implements Serializable {
    private static final long serialVersionUID = 2305800583114768795L;

    private String name;
    private int page = 1;
    private int rows = 20;
    private String sort;
    private String order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
