package org.springclouddev.drools.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.core.tool.node.TreeNode;
import org.springclouddev.drools.entity.DroolsGroup;
import org.springclouddev.drools.vo.DroolsGroupVO;

import java.util.List;

/**
 * 服务类
 *
 * @author zhaobohao
 * @since 2020-05-11
 */
public interface IDroolsGroupService extends BaseService<DroolsGroup> {

    /**
     * 自定义分页
     *
     * @param page
     * @param droolsGroup
     * @return
     */
    IPage<DroolsGroupVO> selectDroolsGroupPage(IPage<DroolsGroupVO> page, DroolsGroupVO droolsGroup);

    List<TreeNode> tree();
}
