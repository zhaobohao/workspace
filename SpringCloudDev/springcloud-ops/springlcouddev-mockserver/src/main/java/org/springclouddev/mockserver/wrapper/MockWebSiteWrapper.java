package org.springclouddev.mockserver.wrapper;

import org.springclouddev.core.mp.support.BaseEntityWrapper;
import org.springclouddev.core.tool.utils.BeanUtil;
import org.springclouddev.mockserver.entity.MockWebSite;
import org.springclouddev.mockserver.vo.MockWebSiteVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 * @since 2020-04-07
 */
public class MockWebSiteWrapper extends BaseEntityWrapper<MockWebSite, MockWebSiteVO> {

    public static MockWebSiteWrapper build() {
        return new MockWebSiteWrapper();
    }

	@Override
	public MockWebSiteVO entityVO(MockWebSite mockWebSite) {
		MockWebSiteVO mockWebSiteVO = BeanUtil.copy(mockWebSite, MockWebSiteVO.class);

		return mockWebSiteVO;
	}

}
