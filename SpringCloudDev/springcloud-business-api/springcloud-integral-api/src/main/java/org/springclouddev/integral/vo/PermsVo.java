package org.springclouddev.integral.vo;

import com.google.common.collect.Lists;
import org.springclouddev.core.secure.utils.SecureUtil;

import java.util.List;

public class PermsVo {

    private Long id;
    private String label;
    private String api;
    private List<PermsVo> children = Lists.newArrayList();

    public PermsVo() {
        this.id = SecureUtil.getUserId();
        this.label = SecureUtil.getUserName();
        this.api =  SecureUtil.getUserRole();
    }

    public PermsVo(Long id, String label, String api) {
        this.id = id;
        this.label = label;
        this.api = api;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public List<PermsVo> getChildren() {
        return children;
    }

    public void addChildren(PermsVo children) {
        this.children.add(children);
    }

    public void setChildren(List<PermsVo> children) {
        this.children = children;
    }
}
