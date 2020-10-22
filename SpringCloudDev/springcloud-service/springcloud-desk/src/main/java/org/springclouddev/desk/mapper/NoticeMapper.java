package org.springclouddev.desk.mapper;

import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.desk.entity.Notice;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author zhaobohao
 * @since 2018-09-29
 */
public interface NoticeMapper extends SuperMapper<Notice> {

	/**
	 * 前N条数据
	 * @param number
	 * @return
	 */
	List<Notice> topList(Integer number);

	/**
	 * 自定义分页
	 * @param page
	 * @param notice
	 * @return
	 */
	List<Notice> selectNoticePage(IPage page, Notice notice);

}
