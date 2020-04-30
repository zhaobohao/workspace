package org.springclouddev.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.integral.entity.GrpList;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 系统组群集合表 Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface GrpListMapper extends SuperMapper<GrpList> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param grpList
	 * @return
	 */
	List<GrpList> selectGrpListPage(IPage page, GrpList grpList);
                                                                            }
