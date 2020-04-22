
package org.springbootdev.core.tool.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * spring 工具类
 *
 * @author zhaobohao
 */
@Slf4j
public class SpringUtil implements ApplicationContextAware {

	private static AbstractApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringUtil.context = (AbstractApplicationContext)context;
	}

	public static <T> T getBean(Class<T> clazz) {
		if (clazz == null) {
			return null;
		}
		return context.getBean(clazz);
	}

	public static <T> T getBean(String beanId) {
		if (beanId == null) {
			return null;
		}
		return (T) context.getBean(beanId);
	}

	public static <T> T getBean(String beanName, Class<T> clazz) {
		if (null == beanName || "".equals(beanName.trim())) {
			return null;
		}
		if (clazz == null) {
			return null;
		}
		return (T) context.getBean(beanName, clazz);
	}

	public static ApplicationContext getContext() {
		if (context == null) {
			return null;
		}
		return context;
	}

	public static void publishEvent(ApplicationEvent event) {
		if (context == null) {
			return;
		}
		try {
			context.publishEvent(event);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}
	/**
	 * 注册bean
	 * @param apply
	 */
	public static void register(Object apply,String beanName){
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
		//BeanDefinitionBuilder构造过程
		//BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(beanClass);
		//builder.addPropertyReference("refBean11", "refBean11");//value是String类型，其实就是beanName
		//builder.addPropertyValue("property222", Object22);//value是object类型，与xml配置一样
		//beanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
		//当前已经new出一个对象了，所以不用像上面二句来生成了。
		beanFactory.registerSingleton(beanName,apply);
	}
}
