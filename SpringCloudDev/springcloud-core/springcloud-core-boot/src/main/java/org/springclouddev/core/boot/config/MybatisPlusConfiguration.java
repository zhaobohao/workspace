
package org.springclouddev.core.boot.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import lombok.AllArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springclouddev.core.mp.base.MyLogicSqlInjector;
import org.springclouddev.core.mp.base.MyMetaObjectHandler;
import org.springclouddev.core.mp.base.TableNameHandleSample;
import org.springclouddev.core.mp.plugins.SqlLogInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mybatisplus 配置
 *
 * @author zhaobohao
 */
@Configuration
@AllArgsConstructor
@MapperScan("org.springclouddev.**.mapper.**")
public class MybatisPlusConfiguration {
	private final TenantLineHandler tenantLineHandler;

	/**
	 * mybatis-plus 拦截器集合
	 */
	@Bean
	@ConditionalOnMissingBean(MybatisPlusInterceptor.class)
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 配置租户拦截器
		interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(tenantLineHandler));
		// 配置分页拦截器
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		return interceptor;
	}

	/**
	 * mybatis-plus自3.4.0起采用新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
	 */
	@Bean
	public ConfigurationCustomizer configurationCustomizer() {
		return configuration -> configuration.setUseDeprecatedExecutor(false);
	}

	/**
	 * sql 日志
	 *
	 * @return SqlLogInterceptor
	 */
	@Bean
	@ConditionalOnProperty(value = "springclouddev.mybatis-plus.sql-log.enabled", matchIfMissing = true)
	public SqlLogInterceptor sqlLogInterceptor() {
		return new SqlLogInterceptor();
	}

	/**
	 * 自定义 SqlInjector
	 * 里面包含自定义的全局方法
	 * 需要在superMapper里声明，才能够使用。
	 */
	@Bean
	public MyLogicSqlInjector myLogicSqlInjector() {
		return new MyLogicSqlInjector();
	}

	/**
	 * 自定义 SqlInjector
	 * 里面包含自定义的全局方法
	 * 需要在superMapper里声明，才能够使用。
	 */
	@Bean
	public MetaObjectHandler myMetaObjectHandler() {
		return new MyMetaObjectHandler();
	}

	/**
	 * sequence主键，需要配置一个主键生成器
	 * 配合实体类注解 KeySequence + TableId type=INPUT
	 * @return
	 */
	@Bean
	public H2KeyGenerator h2KeyGenerator(){
		return new H2KeyGenerator();
	}

	/**
	 *  防止全表更新(删除).可以看BlockAttackSqlParser的源代码。
	 * @return
	 */
	@Bean
	public SqlExplainInterceptor sqlExplainInterceptor(){
		SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();

		//动态替换表名
		DynamicTableNameParser dynamicTableNameParser=new DynamicTableNameParser();
		Map<String, ITableNameHandler> sqlmap=new HashMap<>();
		//具体替换策略在TableNameHandleSample
		sqlmap.put("table_replace",new TableNameHandleSample());
		dynamicTableNameParser.setTableNameHandlerMap(sqlmap);
		List<ISqlParser> sqlParserList = new ArrayList<>();
		//防止整库删除
		sqlParserList.add(new BlockAttackSqlParser());
		//动态表名
		sqlParserList.add(dynamicTableNameParser);

		sqlExplainInterceptor.setSqlParserList(sqlParserList);
		return sqlExplainInterceptor;
	}


}

