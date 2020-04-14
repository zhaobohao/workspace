
package org.springbootdev.modules.mockserver.wrapper;

import org.springbootdev.core.mp.support.BaseEntityWrapper;
import org.springbootdev.core.tool.utils.BeanUtil;
import org.springbootdev.modules.mockserver.entity.MockWebSite;
import org.springbootdev.modules.mockserver.vo.MockWebSiteVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 * @since 2020-04-07
 */
public class MockWebSiteWrapper extends BaseEntityWrapper<MockWebSite, MockWebSiteVO>  {

    public static MockWebSiteWrapper build() {
        return new MockWebSiteWrapper();
    }

	@Override
	public MockWebSiteVO entityVO(MockWebSite mockWebSite) {
		MockWebSiteVO mockWebSiteVO = BeanUtil.copy(mockWebSite, MockWebSiteVO.class);

		return mockWebSiteVO;
	}

}
