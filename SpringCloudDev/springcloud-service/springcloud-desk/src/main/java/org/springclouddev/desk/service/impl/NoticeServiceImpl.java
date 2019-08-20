
package org.springclouddev.desk.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.desk.mapper.NoticeMapper;
import org.springclouddev.desk.entity.Notice;
import org.springclouddev.desk.service.INoticeService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author firewan
 * @since 2018-09-29
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements INoticeService {

	@Override
	public IPage<Notice> selectNoticePage(IPage<Notice> page, Notice notice) {
		return page.setRecords(baseMapper.selectNoticePage(page, notice));
	}

}
