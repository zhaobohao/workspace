package org.springbootdev.modules.drools.configure;

import org.springbootdev.modules.drools.jms.DroolsClearListener;
import org.springbootdev.modules.drools.jms.DroolsReceiveListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 增加redis 的 topic listener
 * @author zhaobo
 */
@Configuration
    public class RedisConfiguration   {
	@Resource
	DroolsClearListener clearListener;
	@Resource
	DroolsReceiveListener receiveListener;
	/**
	 * 消息适配器
	 *
	 * 绑定消息监听者和接收监听的方法,必须要注入这个监听器，不然会报错
	 * @return MessageListenerAdapter
	 */
	@Bean
	public MessageListenerAdapter receiveListenerAdapter() {
		return new MessageListenerAdapter(receiveListener, "receiveMessage");
	}
	/**
	 * 消息适配器
	 *
	 * 绑定消息监听者和接收监听的方法,必须要注入这个监听器，不然会报错
	 * @return MessageListenerAdapter
	 */
	@Bean
	public MessageListenerAdapter clearListenerAdapter() {
		return new MessageListenerAdapter(clearListener, "receiveMessage");
	}

}
