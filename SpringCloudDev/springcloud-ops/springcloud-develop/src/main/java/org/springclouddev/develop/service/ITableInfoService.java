
package org.springclouddev.develop.service;

import org.springclouddev.develop.entity.TableInfo;
import org.springclouddev.develop.vo.TableInfoVO;
import org.springclouddev.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

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
