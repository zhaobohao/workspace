package org.springclouddev.develop.mapper;

import org.springclouddev.core.mp.base.SuperMapper;
import org.springclouddev.develop.entity.DbInstance;
import org.springclouddev.develop.vo.DbInstanceVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
