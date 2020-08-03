package com.dc3.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author pnoker
 */
@Data
public class NodeDto implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int id;
    protected int parentId;
    protected List<NodeDto> children = new ArrayList<>();

    public void add(NodeDto node) {
        children.add(node);
    }
}
