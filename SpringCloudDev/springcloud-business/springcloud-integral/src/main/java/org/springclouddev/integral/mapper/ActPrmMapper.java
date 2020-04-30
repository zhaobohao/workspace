package org.springclouddev.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.integral.entity.ActPrm;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 系统活动参数表 Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface ActPrmMapper extends SuperMapper<ActPrm> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param actPrm
	 * @return
	 */
	List<ActPrm> selectActPrmPage(IPage page, ActPrm actPrm);
                                                                                                                                                            }
