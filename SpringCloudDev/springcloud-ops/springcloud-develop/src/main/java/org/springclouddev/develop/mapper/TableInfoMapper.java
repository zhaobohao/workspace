package org.springclouddev.develop.mapper;

import org.springclouddev.core.mp.base.SuperMapper;
import org.springclouddev.develop.entity.TableInfo;
import org.springclouddev.develop.vo.TableInfoVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
