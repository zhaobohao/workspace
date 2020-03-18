package org.springclouddev.core.boot.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 用来演示redission的使用。
 */
@Component
public class RedissionTip  implements CommandLineRunner {
    @Autowired
	RedissonClient redissonClient;
	@Override
	public void run(String... args) throws Exception {
		RLock rLock = redissonClient.getLock("test1");
		rLock.lock();
		try {
			Thread.sleep(1000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		rLock.unlock();
		System.out.println("redission is ready!");
//        RBloomFilter<String> bloomFilter=redissonClient.getBloomFilter("people");
//        // 初始化布隆过滤器，预计统计元素数量为55000000，期望误差率为0.03
//        bloomFilter.tryInit(55000000L, 0.03);
//        bloomFilter.add("zhaobo");
//        bloomFilter.add("yangxi");
//        System.out.println(bloomFilter.contains("zhaoyiyang"));
//        System.out.println(bloomFilter.contains("zhaobo"));
	}
}
