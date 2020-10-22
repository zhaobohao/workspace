package org.springclouddev.mockserver.wrapper;


import org.springclouddev.core.mp.support.BaseEntityWrapper;
import org.springclouddev.core.tool.utils.BeanUtil;
import org.springclouddev.mockserver.entity.MockHttp;
import org.springclouddev.mockserver.vo.MockHttpVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 * @since 2020-04-07
 */
public class MockHttpWrapper extends BaseEntityWrapper<MockHttp, MockHttpVO> {

    public static MockHttpWrapper build() {
        return new MockHttpWrapper();
    }

	@Override
	public MockHttpVO entityVO(MockHttp mockHttp) {
		MockHttpVO mockHttpVO = BeanUtil.copy(mockHttp, MockHttpVO.class);

		return mockHttpVO;
	}

}
