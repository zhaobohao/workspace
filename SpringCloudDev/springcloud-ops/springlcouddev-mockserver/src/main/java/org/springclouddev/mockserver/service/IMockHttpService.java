
package org.springclouddev.mockserver.service;

import org.springclouddev.mockserver.entity.MockHttp;
import org.springclouddev.mockserver.vo.MockHttpVO;
import org.springclouddev.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
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
