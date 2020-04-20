package org.springclouddev.mockserver.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.core.mp.base.SuperMapper;
import org.springclouddev.mockserver.entity.MockHttp;
import org.springclouddev.mockserver.vo.MockHttpVO;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-04-07
 */
@Mapper
public interface MockHttpMapper extends SuperMapper<MockHttp> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param mockHttp
	 * @return
	 */
	List<MockHttpVO> selectMockHttpPage(IPage page, MockHttpVO mockHttp);
                                                                                                                                                                                                                    }
