package org.springclouddev.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.integral.entity.GrpCls;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 系统组群表 Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface GrpClsMapper extends SuperMapper<GrpCls> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param grpCls
	 * @return
	 */
	List<GrpCls> selectGrpClsPage(IPage page, GrpCls grpCls);
                                                                            }
