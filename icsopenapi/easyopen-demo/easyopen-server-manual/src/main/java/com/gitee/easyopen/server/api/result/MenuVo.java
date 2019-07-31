package com.gitee.easyopen.server.api.result;

import com.gitee.easyopen.doc.annotation.ApiDocField;

import java.util.List;

/**
 * @author tanghc
 */
public class MenuVo {
    @ApiDocField(description = "id")
    private int id;

    @ApiDocField(description = "名称")
    private String name;

    @ApiDocField(description = "子菜单", elementClass = MenuVo.class)
    private List<MenuVo> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVo> children) {
        this.children = children;
    }
}
