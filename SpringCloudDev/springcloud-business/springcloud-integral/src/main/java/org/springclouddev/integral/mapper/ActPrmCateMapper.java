package org.springclouddev.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.integral.entity.ActPrmCate;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 系统活动参数类别项表 Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface ActPrmCateMapper extends SuperMapper<ActPrmCate> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param actPrmCate
	 * @return
	 */
	List<ActPrmCate> selectActPrmCatePage(IPage page, ActPrmCate actPrmCate);
                                                                            }
