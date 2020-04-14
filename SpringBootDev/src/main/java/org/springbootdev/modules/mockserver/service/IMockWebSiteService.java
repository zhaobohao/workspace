
package org.springbootdev.modules.mockserver.service;

import org.springbootdev.core.tool.node.TreeNode;
import org.springbootdev.modules.mockserver.entity.MockWebSite;
import org.springbootdev.modules.mockserver.vo.MockWebSiteVO;
import org.springbootdev.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
