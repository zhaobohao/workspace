package com.gitee.easyopen.doc;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author tanghc
 */
public class ApiModule implements Orderable {

    private String name;
    private int order;
    private List<ApiDocItem> moduleItems = new CopyOnWriteArrayList<>();

    public ApiModule(String name) {
        super();
        this.name = name;
        this.order = Integer.MAX_VALUE;
    }

    public ApiModule(String name, int order) {
        super();
        this.name = name;
        this.order = order;
    }

    @Override
    public String getOrderName() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }


    public List<ApiDocItem> getModuleItems() {
        return moduleItems;
    }

    public void setModuleItems(List<ApiDocItem> moduleItems) {
        this.moduleItems = moduleItems;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ApiModule other = (ApiModule) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
