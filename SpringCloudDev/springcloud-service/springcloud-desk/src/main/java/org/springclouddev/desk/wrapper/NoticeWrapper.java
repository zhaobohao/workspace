package org.springclouddev.desk.wrapper;

import org.springclouddev.core.mp.support.BaseEntityWrapper;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.utils.BeanUtil;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.core.tool.utils.SpringUtil;
import org.springclouddev.desk.entity.Notice;
import org.springclouddev.desk.vo.NoticeVO;
import org.springclouddev.system.feign.IDictClient;

/**
 * Notice包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 */
public class NoticeWrapper extends BaseEntityWrapper<Notice, NoticeVO> {

	private static IDictClient dictClient;

	static {
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	public static NoticeWrapper build() {
		return new NoticeWrapper();
	}

	@Override
	public NoticeVO entityVO(Notice notice) {
		NoticeVO noticeVO = BeanUtil.copy(notice, NoticeVO.class);
		R<String> dict = dictClient.getValue("notice", Func.toStr(noticeVO.getCategory()));
		if (dict.isSuccess()) {
			String categoryName = dict.getData();
			noticeVO.setCategoryName(categoryName);
		}
		return noticeVO;
	}

}
