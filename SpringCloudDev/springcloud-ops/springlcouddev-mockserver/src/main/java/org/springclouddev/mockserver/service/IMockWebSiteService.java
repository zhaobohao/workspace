
package org.springclouddev.mockserver.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.core.tool.node.TreeNode;
import org.springclouddev.mockserver.entity.MockWebSite;
import org.springclouddev.mockserver.vo.MockWebSiteVO;

import java.util.List;

/**
 *  服务类
 *
 * @author zhaobohao
 * @since 2020-04-07
 */
public interface IMockWebSiteService extends BaseService<MockWebSite> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param mockWebSite
	 * @return
	 */
	IPage<MockWebSiteVO> selectMockWebSitePage(IPage<MockWebSiteVO> page, MockWebSiteVO mockWebSite);

    List<TreeNode> tree();
}
