
package org.springbootdev.modules.desk.mapper;

import org.springbootdev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.modules.desk.entity.Notice;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author merryChen
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
