package org.springclouddev.develop.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springclouddev.develop.entity.Code;

/**
 * 服务类
 *
 * @author zhaobohao
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
