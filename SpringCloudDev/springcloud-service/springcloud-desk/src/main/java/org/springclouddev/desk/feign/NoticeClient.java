package org.springclouddev.desk.feign;

import lombok.AllArgsConstructor;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.desk.mapper.NoticeMapper;
import org.springclouddev.desk.entity.Notice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Notice Feign
 *
 * @author zhaobohao
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
public class NoticeClient implements INoticeClient {

	NoticeMapper mapper;

	@Override
	@GetMapping(API_PREFIX + "/top")
	public R<List<Notice>> top(Integer number) {
		return R.data(mapper.topList(number));
	}

}
