
package org.springclouddev.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据传输对象实体类
 *
 * @author firewan
 */
@Data
public class MenuDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String alias;
	private String path;
}
