package org.springclouddev.develop.vo;

import org.springclouddev.develop.entity.DbInstance;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 视图实体类
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DbInstanceVO对象", description = "DbInstanceVO对象")
public class DbInstanceVO extends DbInstance {
	private static final long serialVersionUID = 1L;

}
