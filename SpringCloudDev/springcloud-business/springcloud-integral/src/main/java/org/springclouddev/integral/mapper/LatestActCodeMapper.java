package org.springclouddev.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.integral.entity.LatestActCode;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 最新活动积分编号表 Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface LatestActCodeMapper extends SuperMapper<LatestActCode> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param latestActCode
	 * @return
	 */
	List<LatestActCode> selectLatestActCodePage(IPage page, LatestActCode latestActCode);
                            }
