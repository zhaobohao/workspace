package org.springclouddev.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.integral.entity.ClientActRel;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 用户活动映射关系表 Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface ClientActRelMapper extends SuperMapper<ClientActRel> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param clientActRel
	 * @return
	 */
	List<ClientActRel> selectClientActRelPage(IPage page, ClientActRel clientActRel);
                                    }
