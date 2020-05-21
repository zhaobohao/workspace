package org.springclouddev.drools.jms;

import lombok.extern.slf4j.Slf4j;
import org.drools.core.impl.KnowledgeBaseImpl;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.codec.SerializationCodec;
import org.springclouddev.core.tool.utils.RegexUtil;
import org.springclouddev.core.tool.utils.SpringUtil;
import org.springclouddev.drools.entity.DroolsRuls;
import org.springclouddev.drools.service.IDroolsRulsService;
import org.springclouddev.drools.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhaobo
 * @date  2020-04-09
 */
@Slf4j
@Component
public class DroolsClearListener {
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    private IDroolsRulsService droolsRulsService;
    public void addClearListener()
    {
        RTopic topic = redissonClient.getTopic(Constants.CHANNEL_DROOLS_SERVER_CLEAR,new SerializationCodec());
        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence charSequence, String id) {
                log.info("id : {} onMessage:{}; Thread: {}",id,charSequence,Thread.currentThread().toString());
                DroolsRuls dr=droolsRulsService.getById(Long.valueOf(id));
                ((KnowledgeBaseImpl)SpringUtil.getBean("kieBase")).removeRule(RegexUtil.findResult("package (.*)",dr.getRuleBody(),1).replaceAll(";",""),RegexUtil.findResult("rule (.*)",dr.getRuleBody(),1).replaceAll("\"",""));
                log.info(" we remove drool, package:{}  . name :{}");
            }
        });
    }
}
