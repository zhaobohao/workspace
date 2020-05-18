package org.springbootdev.modules.drools.service.impl;

import org.springbootdev.core.mp.base.BaseServiceImpl;
import org.springbootdev.core.tool.node.TreeNode;
import org.springbootdev.modules.drools.entity.DroolsGroup;
import org.springbootdev.modules.drools.mapper.DroolsGroupMapper;
import org.springbootdev.modules.drools.service.IDroolsGroupService;
import org.springbootdev.modules.drools.vo.DroolsGroupVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

/**
 *  服务实现类
 *
 * @author zhaobohao
 * @since 2020-05-11
 */
@Service
@Slf4j
public class DroolsGroupServiceImpl extends BaseServiceImpl<DroolsGroupMapper, DroolsGroup> implements IDroolsGroupService {

	@Override
	public IPage<DroolsGroupVO> selectDroolsGroupPage(IPage<DroolsGroupVO> page, DroolsGroupVO droolsGroup) {
		return page.setRecords(baseMapper.selectDroolsGroupPage(page, droolsGroup));
	}

    @Override
    public List<TreeNode> tree( ){
        List<TreeNode> retList=new LinkedList<TreeNode>();
        List<DroolsGroup> droolsGroupList=	this.list();
        droolsGroupList.forEach(droolsGroup -> {
            TreeNode treeNode=new TreeNode();
            treeNode.setId(droolsGroup.getId());
            treeNode.setIsLeaf(1);
            treeNode.setParentId(0L);
            treeNode.setTitle(droolsGroup.getName());
//			tableInfoVO.setChildren(baseMapper.selectList(Condition.getQueryWrapper(new TableInfo().setDbInstanceId(tableInfoVO.getId()).setCategory(1))).stream().map(tableinfo->TableInfoWrapper.build().entityVO(tableinfo)).collect(Collectors.toList()));
            retList.add(treeNode);
        });
        return retList;
    }
}
