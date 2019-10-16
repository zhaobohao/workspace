package com.gitee.sop.bookweb.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BookVO {
    @ApiModelProperty(value = "图书id", example = "1")
    private int id;

    @ApiModelProperty(value = "图书名称", example = "白雪公主")
    private String name;

    @ApiModelProperty(value = "isbn", example = "xxxxxx")
    private String isbn;
}
