package org.springclouddev.mockserver.mapper;

import org.springclouddev.mockserver.entity.MockWebSite;
import org.springclouddev.mockserver.vo.MockWebSiteVO;
import org.springclouddev.core.mp.base.SuperMapper;
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
