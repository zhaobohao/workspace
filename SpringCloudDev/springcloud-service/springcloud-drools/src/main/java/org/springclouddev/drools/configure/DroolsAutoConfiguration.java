package org.springclouddev.drools.configure;

import org.drools.core.impl.KnowledgeBaseImpl;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springclouddev.drools.entity.DroolsRuls;
import org.springclouddev.drools.service.IDroolsRulsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.List;

/**
 * Created by  zhaobo on 2019/9/27.
 */
@Configuration
@Order(200)
public class DroolsAutoConfiguration {
	/** 特别要注意 rule的存放位置  */
	private static final String RULES_PATH = "rules/";
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
	private IDroolsRulsService droolsRulsService;
	@Bean
	@ConditionalOnMissingBean(KieHelper.class)
	public KieHelper kieHelper() throws  IOException
 	{
		KieHelper helper = new KieHelper();
		//从配置文件中加载规则
		for (Resource file : getRuleFiles()) {
				String path = file.getURI().toString().substring(file.getURI().toString().indexOf(RULES_PATH));
				helper.addFromClassPath("/" + path, "UTF-8");
		}
		//从数据库中加载规则
		List<DroolsRuls> droolsrulesList=droolsRulsService.list();
		droolsrulesList.stream().forEach(rule->{
			helper.addContent(rule.getRuleBody(), ResourceType.DRL);
		});

		//注册 KieSession kSession
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) ((AbstractApplicationContext)applicationContext).getBeanFactory();
		KnowledgeBaseImpl kieBase =(KnowledgeBaseImpl) helper.build();
		beanFactory.registerSingleton("kieBase",kieBase);
		KieSession session = kieBase.newKieSession();
		beanFactory.registerSingleton("kieSession",session);
		return helper;
	}

	/**
	 * 这里要引入 org.springframework.core.io.Resource  包
	 *
	 * @return
	 * @throws IOException
	 */
	private Resource[] getRuleFiles() throws IOException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		return resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "**/*.drl");
	}
}

