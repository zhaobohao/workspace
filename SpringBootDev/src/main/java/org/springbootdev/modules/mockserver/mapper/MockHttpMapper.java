package org.springbootdev.modules.mockserver.mapper;

import org.springbootdev.modules.mockserver.entity.MockHttp;
import org.springbootdev.modules.mockserver.vo.MockHttpVO;
import org.springbootdev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
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
