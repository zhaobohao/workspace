
package org.springclouddev.mockserver.service.impl;

import org.springclouddev.mockserver.entity.MockHttp;
import org.springclouddev.mockserver.vo.MockHttpVO;
import org.springclouddev.mockserver.mapper.MockHttpMapper;
import org.springclouddev.mockserver.service.IMockHttpService;
import org.springclouddev.core.mp.base.BaseServiceImpl;
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
