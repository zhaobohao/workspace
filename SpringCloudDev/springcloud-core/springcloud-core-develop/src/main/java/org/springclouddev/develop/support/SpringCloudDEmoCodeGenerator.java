package org.springclouddev.develop.support;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.core.tool.utils.StringUtil;
import org.springclouddev.develop.constant.DevelopConstant;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 代码生成器配置类
 *
 * @author zhaobohao
 */
@Data
@Slf4j
public class SpringCloudDEmoCodeGenerator {
	/**
	 * 代码所在系统
	 */
	private String systemName = DevelopConstant.WEB_VUE_NAME;
	/**
	 * 代码模块名称
	 */
	private String codeName;
	/**
	 * 代码所在服务名
	 */
	private String serviceName = "springcloud-service";
	/**
	 * 代码生成的包名
	 */
	private String packageName = "org.springclouddev.test";
	/**
	 * 代码后端生成的地址
	 */
	private String packageDir;
	/**
	 * 代码前端生成的地址
	 */
	private String packageWebDir;
	/**
	 * 需要去掉的表前缀
	 */
	private String[] tablePrefix = {"mk_"};
	/**
	 * 需要生成的表名(两者只能取其一)
	 */
	private String[] includeTables = {"mk_test"};
	/**
	 * 需要排除的表名(两者只能取其一)
	 */
	private String[] excludeTables = {};
	/**
	 * 是否包含基础业务字段
	 */
	private Boolean hasSuperEntity = Boolean.FALSE;
	/**
	 * 是否包含包装器
	 */
	private Boolean hasWrapper = Boolean.FALSE;
	/**
	 * 基础业务字段
	 */
	private String[] superEntityColumns = {"create_time", "create_user", "create_dept", "update_time", "update_user", "status", "is_deleted"};
	/**
	 * 租户字段
	 */
	private String tenantColumn = "tenant_id";
	/**
	 * 是否启用swagger
	 */
	private Boolean isSwagger2 = Boolean.TRUE;
	/**
	 * 数据库驱动名
	 */
	private String driverName;
	/**
	 * 数据库链接地址
	 */
	private String url;
	/**
	 * 数据库用户名
	 */
	private String username;
	/**
	 * 数据库密码
	 */
	private String password;

	/**
	 * 外部注入新的模板引擎
	 */
	private AbstractTemplateEngine templateEngine;

	public void run() {
		Properties props = getProperties();
		AutoGenerator mpg = new AutoGenerator();
		if (null != templateEngine) {
			mpg.setTemplateEngine(templateEngine);
		}
		GlobalConfig gc = new GlobalConfig();
		String outputDir = getOutputDir();
		String author = props.getProperty("author");
		gc.setOutputDir(outputDir);
		gc.setAuthor(author);
		gc.setFileOverride(true);
		gc.setOpen(false);
		gc.setActiveRecord(false);
		gc.setEnableCache(false);
		gc.setBaseResultMap(true);
		gc.setBaseColumnList(true);
		gc.setMapperName("%sMapper");
		gc.setXmlName("%sMapper");
		gc.setServiceName("I%sService");
		gc.setServiceImplName("%sServiceImpl");
		gc.setControllerName("%sController");
		gc.setSwagger2(isSwagger2);
		mpg.setGlobalConfig(gc);
		DataSourceConfig dsc = new DataSourceConfig();
		String driverName = Func.toStr(this.driverName, props.getProperty("spring.datasource.driver-class-name"));
		if (StringUtil.containsAny(driverName, DbType.MYSQL.getDb())) {
			dsc.setDbType(DbType.MYSQL);
			dsc.setTypeConvert(new MySqlTypeConvert());
		} else if (StringUtil.containsAny(driverName, DbType.POSTGRE_SQL.getDb())) {
			dsc.setDbType(DbType.POSTGRE_SQL);
			dsc.setTypeConvert(new PostgreSqlTypeConvert());
		} else {
			dsc.setDbType(DbType.ORACLE);
			dsc.setTypeConvert(new OracleTypeConvert());
		}
		dsc.setDriverName(driverName);
		dsc.setUrl(Func.toStr(this.url, props.getProperty("spring.datasource.url")));
		dsc.setUsername(Func.toStr(this.username, props.getProperty("spring.datasource.username")));
		dsc.setPassword(Func.toStr(this.password, props.getProperty("spring.datasource.password")));
		mpg.setDataSource(dsc);
		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		// strategy.setCapitalMode(true);// 全局大写命名
		// strategy.setDbColumnUnderline(true);//全局下划线命名
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setTablePrefix(tablePrefix);
		// 设置自动填充字段
		List<TableFill> tableFillList=new LinkedList<>();
		tableFillList.add(new TableFill("create_user", FieldFill.INSERT));
		tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
		tableFillList.add(new TableFill("update_user", FieldFill.UPDATE));
		tableFillList.add(new TableFill("update_time", FieldFill.UPDATE));
		tableFillList.add(new TableFill("status", FieldFill.INSERT));
		tableFillList.add(new TableFill("is_deleted", FieldFill.INSERT));
		tableFillList.add(new TableFill("tenant_id", FieldFill.INSERT));
		tableFillList.add(new TableFill("is_leaf", FieldFill.INSERT));
		tableFillList.add(new TableFill("parent_id", FieldFill.INSERT));
		tableFillList.add(new TableFill("sort", FieldFill.INSERT));

		strategy.setTableFillList(tableFillList);
		if (includeTables.length > 0) {
			strategy.setInclude(includeTables);
		}
		if (excludeTables.length > 0) {
			strategy.setExclude(excludeTables);
		}
		if (hasSuperEntity) {
			strategy.setSuperEntityClass("org.springclouddev.core.mp.base.BaseEntity");
			strategy.setSuperEntityColumns(superEntityColumns);
			strategy.setSuperMapperClass("org.springclouddev.core.mp.base.SuperMapper");
			strategy.setSuperServiceClass("org.springclouddev.core.mp.base.BaseService");
			strategy.setSuperServiceImplClass("org.springclouddev.core.mp.base.BaseServiceImpl");
		} else {
			strategy.setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService");
			strategy.setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
		}
		// 自定义 controller 父类
		strategy.setSuperControllerClass("org.springclouddev.core.boot.ctrl.AbstractController");
		strategy.setEntityBuilderModel(false);
		strategy.setEntityLombokModel(true);
		strategy.setControllerMappingHyphenStyle(true);
		strategy.setLogicDeleteFieldName("is_deleted");
		strategy.setVersionFieldName("version");
		strategy.setEntityTableFieldAnnotationEnable(true);
		mpg.setStrategy(strategy);
		// 包配置packageConfig pc = new PackageConfig();
		// 控制台扫描
		pc.setModuleName(null);
		pc.setParent(packageName);
		pc.setController("controller");
		pc.setEntity("entity");
		pc.setXml("mapper");
		mpg.setPackageInfo(pc);
		mpg.setCfg(getInjectionConfig());
		mpg.execute();
	}

	private InjectionConfig getInjectionConfig() {
		String servicePackage = serviceName.split("-").length > 1 ? serviceName.split("-")[1] : serviceName;
		// 自定义配置
		Map<String, Object> map = new HashMap<>(16);
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				map.put("codeName", codeName);
				map.put("serviceName", serviceName);
				map.put("servicePackage", servicePackage);
				map.put("tenantColumn", tenantColumn);
				map.put("hasWrapper", hasWrapper);
				this.setMap(map);
			}
		};
		List<FileOutConfig> focList = new ArrayList<>();
		focList.add(new FileOutConfig("/templates/sql/menu.sql.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				map.put("entityKey", (tableInfo.getEntityName().toLowerCase()));
				map.put("menuId", IdWorker.getId());
				map.put("addMenuId", IdWorker.getId());
				map.put("editMenuId", IdWorker.getId());
				map.put("removeMenuId", IdWorker.getId());
				map.put("viewMenuId", IdWorker.getId());
				return getOutputDir() + "/" + "/sql/" + tableInfo.getEntityName().toLowerCase() + ".menu.mysql";
			}
		});
		focList.add(new FileOutConfig("/templates/entityVO.java.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				return getOutputDir() + "/" + packageName.replace(".", "/") + "/" + "vo" + "/" + tableInfo.getEntityName() + "VO" + StringPool.DOT_JAVA;
			}
		});
		focList.add(new FileOutConfig("/templates/entityDTO.java.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				return getOutputDir() + "/" + packageName.replace(".", "/") + "/" + "dto" + "/" + tableInfo.getEntityName() + "DTO" + StringPool.DOT_JAVA;
			}
		});
		if (hasWrapper) {
			focList.add(new FileOutConfig("/templates/wrapper.java.vm") {
				@Override
				public String outputFile(TableInfo tableInfo) {
					return getOutputDir() + "/" + packageName.replace(".", "/") + "/" + "wrapper" + "/" + tableInfo.getEntityName() + "Wrapper" + StringPool.DOT_JAVA;
				}
			});
		}
		if (Func.isNotBlank(packageWebDir)) {
			if (Func.equals(systemName, DevelopConstant.WEB_VUE_NAME)) {
				focList.add(new FileOutConfig("/templates/vue/api.js.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						return getOutputWebDir() + "/api" + "/" + servicePackage.toLowerCase() + "/" + tableInfo.getEntityName().toLowerCase() + ".js";
					}
				});
				focList.add(new FileOutConfig("/templates/vue/entity.js.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						return getOutputWebDir() + "/entitys" + "/" + servicePackage.toLowerCase() + "/" + tableInfo.getEntityName().toLowerCase() + ".js";
					}
				});
				focList.add(new FileOutConfig("/templates/vue/index.vue.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						return getOutputWebDir() + "/views" + "/" + servicePackage.toLowerCase()+ "/" + tableInfo.getEntityName().toLowerCase() +  "/index.vue";
					}
				});
				focList.add(new FileOutConfig("/templates/vue/components/searchCard.vue.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						return getOutputWebDir() + "/views" + "/" + servicePackage.toLowerCase() + "/" + tableInfo.getEntityName().toLowerCase() +  "/components/searchCard.vue";
					}
				});
				focList.add(new FileOutConfig("/templates/vue/components/listTable.vue.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						return getOutputWebDir() + "/views" + "/" + servicePackage.toLowerCase() + "/" + tableInfo.getEntityName().toLowerCase() +  "/components/listTable.vue";
					}
				});
				focList.add(new FileOutConfig("/templates/vue/components/dataForm.vue.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						return getOutputWebDir() + "/views" + "/" + servicePackage.toLowerCase() + "/" + tableInfo.getEntityName().toLowerCase() +  "/components/dataForm.vue";
					}
				});
			}
		}
		cfg.setFileOutConfigList(focList);
		return cfg;
	}

	/**
	 * 获取配置文件
	 *
	 * @return 配置Props
	 */
	private Properties getProperties() {
		// 读取配置文件
		Resource resource = new ClassPathResource("/templates/code.properties");
		Properties props = new Properties();
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	/**
	 * 生成到项目中
	 *
	 * @return outputDir
	 */
	public String getOutputDir() {
		return (Func.isBlank(packageDir) ? System.getProperty("user.dir") + "/springcloud-ops/springcloud-develop" : packageDir) + "/src/main/java";
	}

	/**
	 * 生成到Web项目中
	 *
	 * @return outputDir
	 */
	public String getOutputWebDir() {
		return (Func.isBlank(packageWebDir) ? System.getProperty("user.dir") : packageWebDir) + "/src";
	}

	/**
	 * 页面生成的文件名
	 */
	private String getGeneratorViewPath(String viewOutputDir, TableInfo tableInfo, String suffixPath) {
		String name = StringUtils.firstToLowerCase(tableInfo.getEntityName());
		String path = viewOutputDir + "/" + name + "/" + name + suffixPath;
		File viewDir = new File(path).getParentFile();
		if (!viewDir.exists()) {
			viewDir.mkdirs();
		}
		return path;
	}

}
