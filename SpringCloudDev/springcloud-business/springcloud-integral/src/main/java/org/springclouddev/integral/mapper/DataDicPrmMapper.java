package org.springclouddev.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.integral.entity.DataDicPrm;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 系统字典参数表 Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface DataDicPrmMapper extends SuperMapper<DataDicPrm> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dataDicPrm
	 * @return
	 */
	List<DataDicPrm> selectDataDicPrmPage(IPage page, DataDicPrm dataDicPrm);
                        
    /**
	 *  获取树形节点,获取指定parentId这一层的数据
	 * @param parentId 如果为空，返回所有树形结构数据
	 * @return
	 */
        List<DataDicPrm> tree(String parentId);
                                                                            }
