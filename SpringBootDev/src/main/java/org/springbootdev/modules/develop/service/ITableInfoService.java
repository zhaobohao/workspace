
package org.springbootdev.modules.develop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.core.mp.base.BaseService;
import org.springbootdev.modules.develop.entity.TableInfo;
import org.springbootdev.modules.develop.vo.TableInfoVO;

import java.util.List;

/**
 *  服务类
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
public interface ITableInfoService extends BaseService<TableInfo> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tableInfo
	 * @return
	 */
	IPage<TableInfoVO> selectTableInfoPage(IPage<TableInfoVO> page, TableInfoVO tableInfo);

    List<TableInfoVO> tree(String parentId);
}
