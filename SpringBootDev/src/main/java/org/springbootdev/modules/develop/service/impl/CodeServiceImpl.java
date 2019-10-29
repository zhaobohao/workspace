
package org.springbootdev.modules.develop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springbootdev.core.tool.constant.ToolConstant;
import org.springbootdev.modules.develop.entity.Code;
import org.springbootdev.modules.develop.mapper.CodeMapper;
import org.springbootdev.modules.develop.service.ICodeService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author merryChen
 */
@Service
public class CodeServiceImpl extends ServiceImpl<CodeMapper, Code> implements ICodeService {

	@Override
	public boolean submit(Code code) {
		code.setIsDeleted(ToolConstant.DB_NOT_DELETED);
		return saveOrUpdate(code);
	}

}
