package org.springclouddev.seata.order.feign;

import org.springframework.stereotype.Component;

/**
 * StorageClientFallback
 *
 * @author zhaobo
 */
@Component
public class StorageClientFallback implements IStorageClient {

	@Override
	public int deduct(String commodityCode, Integer count) {
		return -1;
	}

}
