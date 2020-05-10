package org.springclouddev.drools.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.core.mp.base.SuperMapper;
import org.springclouddev.drools.entity.DroolsRuls;
import org.springclouddev.drools.vo.DroolsRulsVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-05-11
 */
@Mapper
public interface DroolsRulsMapper extends SuperMapper<DroolsRuls> {

    /**
     * 自定义分页
     *
     * @param page
     * @param droolsRuls
     * @return
     */
    List<DroolsRulsVO> selectDroolsRulsPage(IPage page, DroolsRulsVO droolsRuls);
}
