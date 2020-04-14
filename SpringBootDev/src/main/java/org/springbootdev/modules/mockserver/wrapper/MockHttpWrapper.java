
package org.springbootdev.modules.mockserver.wrapper;

import org.springbootdev.core.mp.support.BaseEntityWrapper;
import org.springbootdev.core.tool.utils.BeanUtil;
import org.springbootdev.modules.mockserver.entity.MockHttp;
import org.springbootdev.modules.mockserver.vo.MockHttpVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 * @since 2020-04-07
 */
public class MockHttpWrapper extends BaseEntityWrapper<MockHttp, MockHttpVO>  {

    public static MockHttpWrapper build() {
        return new MockHttpWrapper();
    }

	@Override
	public MockHttpVO entityVO(MockHttp mockHttp) {
		MockHttpVO mockHttpVO = BeanUtil.copy(mockHttp, MockHttpVO.class);

		return mockHttpVO;
	}

}
