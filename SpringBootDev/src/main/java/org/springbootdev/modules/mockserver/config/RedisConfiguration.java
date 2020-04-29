package org.springbootdev.modules.mockserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;

/**
 * @author zhaobo
 **/
@Configuration
public class RedisConfiguration {

	@Resource
	ReceiverMockServerInterface receiver;
	@Resource
	ClearMockServerInterface clear;
	/**
	 * 消息适配器
	 *
	 * 绑定消息监听者和接收监听的方法,必须要注入这个监听器，不然会报错
	 * @return MessageListenerAdapter
	 */
	@Bean
	public MessageListenerAdapter receiveListenerAdapter() {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
	/**
	 * 消息适配器
	 *
	 * 绑定消息监听者和接收监听的方法,必须要注入这个监听器，不然会报错
	 * @return MessageListenerAdapter
	 */
	@Bean
	public MessageListenerAdapter clearListenerAdapter() {
		return new MessageListenerAdapter(clear, "receiveMessage");
	}

	/**
	 * 定义消息监听者容器
	 * @param connectionFactory 连接工厂
	 * @param receiveListenerAdapter 消息处理器
	 * @param clearListenerAdapter 消息处理器
	 * @return RedisMessageListenerContainer
	 */
	@Bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
												   MessageListenerAdapter receiveListenerAdapter,
												   MessageListenerAdapter clearListenerAdapter
												   ) {
		RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
		listenerContainer.setConnectionFactory(connectionFactory);
		listenerContainer.addMessageListener(receiveListenerAdapter, new PatternTopic(Constants.CHANNEL_MOCK_SERVER_RECEIVE));
		listenerContainer.addMessageListener(clearListenerAdapter, new PatternTopic(Constants.CHANNEL_MOCK_SERVER_CLEAR));
		return listenerContainer;
	}

}
