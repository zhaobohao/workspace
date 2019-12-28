
package org.springbootdev.core.boot.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springbootdev.core.launch.constant.AppConstant;
import org.springbootdev.core.mp.base.MyLogicSqlInjector;
import org.springbootdev.core.mp.base.MyMetaObjectHandler;
import org.springbootdev.core.mp.base.TableNameHandleSample;
import org.springbootdev.core.mp.plugins.SqlLogInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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
@MapperScan("org.springbootdev.**.mapper.**")
public class MybatisPlusConfiguration {

	/**
	 * 1.分页插件
	 * 2.多租户
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		// 开启 count 的 join 优化,只针对 left join !!!
		return new PaginationInterceptor().setCountSqlParser(new JsqlParserCountOptimize(true));
	}
	/**
	 * sql 日志
	 */
	@Bean
	@ConditionalOnProperty(value = "blade.mybatis-plus.sql-log.enable", matchIfMissing = true)
	public SqlLogInterceptor sqlLogInterceptor() {
		return new SqlLogInterceptor();
	}
	@Bean
	public OptimisticLockerInterceptor optimisticLockerInterceptor(){
		return new OptimisticLockerInterceptor();
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

