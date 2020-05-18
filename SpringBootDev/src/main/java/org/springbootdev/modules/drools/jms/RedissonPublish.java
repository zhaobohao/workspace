package org.springbootdev.modules.drools.jms;

import org.springbootdev.modules.drools.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zhaobo
 * @date
 */
@Component
public class RedissonPublish {

	@Autowired
	private RedisTemplate j2CacheRedisTemplate;
    /**
     * create by: zhaobo
     * description: 删除已有的规则
     * create time: 2020/5/18 14:55
     *
     * @Param: id
     * @return void
     */
    public  void  publishClearTopic(String id)
    {
		j2CacheRedisTemplate.convertAndSend(Constants.CHANNEL_DROOLS_SERVER_CLEAR,  id);
    }

    /**
     * create by: zhaobo
     * description: 创建新的规则，供后续调用。
     * create time: 2020/5/18 14:54
     *
     * @Param: id
     * @return void
     */
    public  void  publishReceiveTopic(String id)
    {
		j2CacheRedisTemplate.convertAndSend(Constants.CHANNEL_DROOLS_SERVER_RECEIVE,  id);
    }


}
