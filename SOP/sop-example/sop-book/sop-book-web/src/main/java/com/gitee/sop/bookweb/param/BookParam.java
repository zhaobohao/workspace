package com.gitee.sop.bookweb.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class BookParam {
    @ApiModelProperty(value = "图书id", example = "1")
    private int id;

    @ApiModelProperty(value = "图书ISBN", example = "xxxx")
    private String isbn;
}
