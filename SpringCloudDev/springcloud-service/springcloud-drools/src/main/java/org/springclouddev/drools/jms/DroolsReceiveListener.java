package org.springclouddev.drools.jms;

import lombok.extern.slf4j.Slf4j;
import org.drools.core.impl.KnowledgeBaseImpl;
import org.kie.api.io.ResourceType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.codec.SerializationCodec;
import org.springclouddev.drools.entity.DroolsRuls;
import org.springclouddev.drools.service.IDroolsRulsService;
import org.springclouddev.drools.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
public class DroolsReceiveListener {
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    KnowledgeBaseImpl kieBase;
    @Autowired
    private IDroolsRulsService droolsRulsService;
    public void addClearListener()
    {
        RTopic topic = redissonClient.getTopic(Constants.CHANNEL_DROOLS_SERVER_RECEIVE,new SerializationCodec());
        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence charSequence, String id) {
               log.info("onMessage:"+charSequence+"; Thread: "+Thread.currentThread().toString());
                DroolsRuls dr=droolsRulsService.getById(Long.valueOf(id));
                //重新添加规则
                KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
                //装入规则，可以装入多个
                try {
                    kb.add(ResourceFactory.newByteArrayResource(dr.getRuleBody().getBytes("utf-8")), ResourceType.DRL);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                kieBase.addPackages(kb.getKnowledgePackages());
                log.info(" we create drool,id : "+id);
            }
        });
    }
}
