
package org.springbootdev.core.mp.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springbootdev.core.secure.SystemUser;
import org.springbootdev.core.secure.utils.SecureUtil;
import org.springbootdev.core.tool.constant.ToolConstant;
import org.springbootdev.core.tool.utils.DateUtil;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * 业务封装基础类
 *
 * @param <M> mapper
 * @param <T> model
 * @author zhaobohao
 */
@Validated
public class BaseServiceImpl<M extends SuperMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {
	@Override
	public boolean deleteLogic(@NotEmpty List<Long> ids) {
		return super.removeByIds(ids);
	}
}
