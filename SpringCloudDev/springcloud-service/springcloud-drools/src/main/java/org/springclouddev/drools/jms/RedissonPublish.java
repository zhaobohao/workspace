package org.springclouddev.drools.jms;

import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.springclouddev.drools.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhaobo
 * @date
 */
@Component
public class RedissonPublish {

    @Autowired
    RedissonClient redissonClient;

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
        redissonClient.getTopic(Constants.CHANNEL_DROOLS_SERVER_CLEAR,new SerializationCodec()).publish(id);
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
        redissonClient.getTopic(Constants.CHANNEL_DROOLS_SERVER_RECEIVE,new SerializationCodec()).publish(id);
    }


}
