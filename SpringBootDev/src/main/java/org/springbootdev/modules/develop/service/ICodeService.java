
package org.springbootdev.modules.develop.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springbootdev.modules.develop.entity.Code;

/**
 * 服务类
 *
 * @author merryChen
 */
public interface ICodeService extends IService<Code> {

	/**
	 * 提交
	 *
	 * @param code
	 * @return
	 */
	boolean submit(Code code);

}
