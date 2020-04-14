
package org.springbootdev.modules.mockserver.service.impl;

import org.springbootdev.modules.mockserver.entity.MockHttp;
import org.springbootdev.modules.mockserver.vo.MockHttpVO;
import org.springbootdev.modules.mockserver.mapper.MockHttpMapper;
import org.springbootdev.modules.mockserver.service.IMockHttpService;
import org.springbootdev.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
/**
 *  服务实现类
 *
 * @author zhaobohao
 * @since 2020-04-07
 */
@Service
@Slf4j
public class MockHttpServiceImpl extends BaseServiceImpl<MockHttpMapper, MockHttp> implements IMockHttpService {

	@Override
	public IPage<MockHttpVO> selectMockHttpPage(IPage<MockHttpVO> page, MockHttpVO mockHttp) {
		return page.setRecords(baseMapper.selectMockHttpPage(page, mockHttp));
	}
                                                                                                                                                                                                                                                                                                                            
}
