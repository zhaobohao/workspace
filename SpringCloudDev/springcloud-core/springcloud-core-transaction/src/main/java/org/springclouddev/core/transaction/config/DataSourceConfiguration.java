package org.springclouddev.core.transaction.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 分布式事务数据源配置
 *
 * @author zhaobo
 */
@Configuration
public class DataSourceConfiguration {

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean(DataSourceProxy dataSourceProxy) throws Exception {
		MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
		bean.setDataSource(dataSourceProxy);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		bean.setMapperLocations(resolver.getResources("classpath:org/springclouddev/**/mapper/*Mapper.xml"));

		SqlSessionFactory factory = null;
		try {
			factory = bean.getObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return factory;
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	/**
	 * 从配置文件获取属性构造datasource
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DruidDataSource druidDataSource() {
		return new DruidDataSource();
	}

	/**
	 * 构造datasource代理对象
	 */
	@Primary
	@Bean("dataSource")
	public DataSourceProxy dataSourceProxy(DataSource druidDataSource) {
		return new DataSourceProxy(druidDataSource);
	}

}
