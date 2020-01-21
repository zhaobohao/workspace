package com.sharding.demo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mallplus.common.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mall
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User extends SuperEntity {
	private static final long serialVersionUID = 8898492657846787286L;
	private String companyId;
	private String name;
}