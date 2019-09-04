

package com.spring.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询参数
 * @author geekidea
 * @since 2018-11-08
 */
@Data
@ApiModel("查询参数对象")
public abstract class QueryParam implements Serializable{
    private static final long serialVersionUID = -3263921252635611410L;
    /**
     * 默认页码为1
     */
    Integer DEFAULT_PAGE_INDEX = 1;

    /**
     * 默认页大小为10
     */
    Integer DEFAULT_PAGE_SIZE = 10;
    @ApiModelProperty(value = "页码,默认为1")
	private Integer current = DEFAULT_PAGE_INDEX;
	@ApiModelProperty(value = "页大小,默认为10")
	private Integer size = DEFAULT_PAGE_SIZE;
    @ApiModelProperty(value = "搜索字符串")
    private String keyword;

    public void setCurrent(Integer current) {
	    if (current == null || current <= 0){
	        this.current = DEFAULT_PAGE_INDEX;
        }else{
            this.current = current;
        }
    }

    public void setSize(Integer size) {
	    if (size == null || size <= 0){
	        this.size = DEFAULT_PAGE_SIZE;
        }else{
            this.size = size;
        }
    }

}
