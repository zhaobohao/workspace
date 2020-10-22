package org.springclouddev.develop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springclouddev.core.tool.constant.ToolConstant;
import org.springclouddev.develop.entity.Code;
import org.springclouddev.develop.mapper.CodeMapper;
import org.springclouddev.develop.service.ICodeService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author zhaobohao
 */
@Service
public class CodeServiceImpl extends ServiceImpl<CodeMapper, Code> implements ICodeService {

	@Override
	public boolean submit(Code code) {
		code.setIsDeleted(ToolConstant.DB_NOT_DELETED);
		return saveOrUpdate(code);
	}

}
