
package org.springbootdev.modules.develop.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.core.mp.base.SuperMapper;
import org.springbootdev.modules.develop.entity.DbInstance;
import org.springbootdev.modules.develop.vo.DbInstanceVO;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
public interface DbInstanceMapper extends SuperMapper<DbInstance> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dbInstance
	 * @return
	 */
	List<DbInstanceVO> selectDbInstancePage(IPage page, DbInstanceVO dbInstance);

}
