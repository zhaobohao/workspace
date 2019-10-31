
package org.springbootdev.modules.desk.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.core.mp.base.BaseServiceImpl;
import org.springbootdev.modules.desk.entity.Notice;
import org.springbootdev.modules.desk.mapper.NoticeMapper;
import org.springbootdev.modules.desk.service.INoticeService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author merryChen
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements INoticeService {

	@Override
	public IPage<Notice> selectNoticePage(IPage<Notice> page, Notice notice) {
		return page.setRecords(SuperMapper.selectNoticePage(page, notice));
	}

}
