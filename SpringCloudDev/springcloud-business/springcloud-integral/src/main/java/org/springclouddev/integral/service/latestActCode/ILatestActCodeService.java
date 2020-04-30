package org.springclouddev.integral.service.latestActCode;

import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.integral.entity.LatestActCode;

import java.util.List;

public interface ILatestActCodeService extends BaseService<LatestActCode> {
	
	List<LatestActCode>  queryActCode();

}
