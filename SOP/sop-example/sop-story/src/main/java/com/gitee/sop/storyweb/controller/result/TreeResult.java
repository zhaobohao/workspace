package com.gitee.sop.storyweb.controller.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author tanghc
 */
@Data
public class TreeResult {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "父id")
    private Integer pid;

    @ApiModelProperty(value = "子节点")
    private List<TreeResult> children;
}
