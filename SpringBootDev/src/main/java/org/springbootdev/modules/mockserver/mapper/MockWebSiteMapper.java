package org.springbootdev.modules.mockserver.mapper;

import org.springbootdev.modules.mockserver.entity.MockWebSite;
import org.springbootdev.modules.mockserver.vo.MockWebSiteVO;
import org.springbootdev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
/**
 *  Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-04-07
 */
@Mapper
public interface MockWebSiteMapper extends SuperMapper<MockWebSite> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param mockWebSite
	 * @return
	 */
	List<MockWebSiteVO> selectMockWebSitePage(IPage page, MockWebSiteVO mockWebSite);
                                    }
