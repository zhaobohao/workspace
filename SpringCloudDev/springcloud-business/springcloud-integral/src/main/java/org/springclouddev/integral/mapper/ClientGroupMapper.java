package org.springclouddev.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.integral.entity.ClientGroup;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 *  Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface ClientGroupMapper extends SuperMapper<ClientGroup> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param clientGroup
	 * @return
	 */
	List<ClientGroup> selectClientGroupPage(IPage page, ClientGroup clientGroup);
                                                            }
