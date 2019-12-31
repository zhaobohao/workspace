package org.springclouddev.seata.storage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * storage
 *
 * @author zhaobo
 */
@Data
@TableName("tb_storage")
public class Storage implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonSerialize(using= ToStringSerializer.class)
private Long id;
	private String commodityCode;
	private Long count;

}
