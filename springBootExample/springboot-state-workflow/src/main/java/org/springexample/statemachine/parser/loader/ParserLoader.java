package org.springexample.statemachine.parser.loader;

import org.springexample.statemachine.core.configurer.StateMachineConfigurer;
import org.springexample.statemachine.core.exception.StateMachineException;
import org.springexample.statemachine.parser.StateMachineParser;
import org.springexample.statemachine.parser.yml.StateMachineYmlConfig;
import org.springexample.statemachine.parser.yml.StateMachineYmlParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 添加
 * Created by zhaobo on 2016/4/10
 * @author zhaobo
 */
@Slf4j
//@Configuration
public class ParserLoader implements BeanFactoryPostProcessor {

    /**
     * Modify the application context's internal bean factory after its standard
     * initialization. All bean definitions will have been loaded, but no beans
     * will have been instantiated yet. This allows for overriding or adding
     * properties even to eager-initializing beans.
     * @param beanFactory the bean factory used by the application context
     * @throws BeansException in case of errors
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            StateMachineParser<StateMachineYmlConfig> stateMachineParser = beanFactory.getBean(StateMachineYmlParser.class);
            //实例化解析器
            Yaml yaml = new Yaml();
            //配置文件地址
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("statemachine\\*statemachine*.yml");
            log.info("find StateMachineYmlConfig " + resources.length + " count");
            for (Resource resource : resources) {
                File file = resource.getFile();
                FileInputStream fileInputStream = new FileInputStream(file);
                StateMachineYmlConfig config = yaml.loadAs(fileInputStream, StateMachineYmlConfig.class);
                log.info("load StateMachineYmlConfig {}", file.getName());
                StateMachineConfigurer stateMachineConfigurer = stateMachineParser.parser(config);
                String name = config.getName();
                if (name == null || name.length() <= 0) {
                    throw new StateMachineException("please defined name with .yml config");
                }
                if (beanFactory.containsBean(name)) {
                    throw new StateMachineException("StateMachine bean name '" + name + "' has conflicts with existing");
                }
                beanFactory.registerSingleton(name, stateMachineConfigurer);
            }
        } catch (FileNotFoundException e) {
            log.info("No StateMachineYmlConfig Found", e);
        } catch (IOException e) {
            throw new BeanCreationException("StateMachine.yml IOException", e);
        }
    }
}
