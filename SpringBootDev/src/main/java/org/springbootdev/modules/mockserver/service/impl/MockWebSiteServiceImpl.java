
package org.springbootdev.modules.mockserver.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springbootdev.core.mp.base.BaseServiceImpl;
import org.springbootdev.core.tool.node.TreeNode;
import org.springbootdev.modules.mockserver.entity.MockWebSite;
import org.springbootdev.modules.mockserver.mapper.MockWebSiteMapper;
import org.springbootdev.modules.mockserver.service.IMockWebSiteService;
import org.springbootdev.modules.mockserver.vo.MockWebSiteVO;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 *  服务实现类
 *
 * @author zhaobohao
 * @since 2020-04-07
 */
@Service
@Slf4j
public class MockWebSiteServiceImpl extends BaseServiceImpl<MockWebSiteMapper, MockWebSite> implements IMockWebSiteService {

	@Override
	public IPage<MockWebSiteVO> selectMockWebSitePage(IPage<MockWebSiteVO> page, MockWebSiteVO mockWebSite) {
		return page.setRecords(baseMapper.selectMockWebSitePage(page, mockWebSite));
	}
	@Override
	public List<TreeNode> tree( ){
		List<TreeNode> retList=new LinkedList<TreeNode>();
		List<MockWebSite> mockWebSiteList=	this.list();
		mockWebSiteList.forEach(mockWebSite -> {
			TreeNode treeNode=new TreeNode();
			treeNode.setId(mockWebSite.getId());
			treeNode.setIsLeaf(1);
			treeNode.setParentId(0L);
			treeNode.setTitle(mockWebSite.getName());
//			tableInfoVO.setChildren(baseMapper.selectList(Condition.getQueryWrapper(new TableInfo().setDbInstanceId(tableInfoVO.getId()).setCategory(1))).stream().map(tableinfo->TableInfoWrapper.build().entityVO(tableinfo)).collect(Collectors.toList()));
			retList.add(treeNode);
		});
		return retList;
	}
}
