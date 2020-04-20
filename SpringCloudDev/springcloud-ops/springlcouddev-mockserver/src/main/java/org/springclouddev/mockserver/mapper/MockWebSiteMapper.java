package org.springclouddev.mockserver.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.core.mp.base.SuperMapper;
import org.springclouddev.mockserver.entity.MockWebSite;
import org.springclouddev.mockserver.vo.MockWebSiteVO;

import java.util.List;

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
