package org.springbootdev.modules.drools.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.springbootdev.core.mp.base.SuperMapper;
import org.springbootdev.modules.drools.entity.DroolsGroup;
import org.springbootdev.modules.drools.vo.DroolsGroupVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-05-11
 */
@Mapper
public interface DroolsGroupMapper extends SuperMapper<DroolsGroup> {

    /**
     * 自定义分页
     *
     * @param page
     * @param droolsGroup
     * @return
     */
    List<DroolsGroupVO> selectDroolsGroupPage(IPage page, DroolsGroupVO droolsGroup);
}
