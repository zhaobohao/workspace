package org.springclouddev.integral.service.impl.latestActCode;

import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.integral.entity.LatestActCode;
import org.springclouddev.integral.mapper.LatestActCodeMapper;
import org.springclouddev.integral.service.latestActCode.ILatestActCodeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LatestActCodeService  extends BaseServiceImpl<LatestActCodeMapper, LatestActCode> implements ILatestActCodeService {

	@Override
	public List<LatestActCode> queryActCode() {
		List<LatestActCode> list = this.list();
		return  list ;
		
	}

}
