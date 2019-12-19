
package org.springbootdev.modules.develop.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.core.mp.base.SuperMapper;
import org.springbootdev.modules.develop.entity.TableInfo;
import org.springbootdev.modules.develop.vo.TableInfoVO;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
public interface TableInfoMapper extends SuperMapper<TableInfo> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tableInfo
	 * @return
	 */
	List<TableInfoVO> selectTableInfoPage(IPage page, TableInfoVO tableInfo);

}
