
package org.springclouddev.mockserver.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.mockserver.entity.MockHttp;
import org.springclouddev.mockserver.vo.MockHttpVO;

/**
 *  服务类
 *
 * @author zhaobohao
 * @since 2020-04-07
 */
public interface IMockHttpService extends BaseService<MockHttp> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param mockHttp
	 * @return
	 */
	IPage<MockHttpVO> selectMockHttpPage(IPage<MockHttpVO> page, MockHttpVO mockHttp);
                                                                                                                                                                                                                                                                                                                            }
