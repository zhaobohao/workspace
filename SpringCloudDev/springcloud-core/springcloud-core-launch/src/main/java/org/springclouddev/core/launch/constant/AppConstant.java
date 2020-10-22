package org.springclouddev.core.launch.constant;

/**
 * 系统常量
 *
 * @author zhaobohao
 */
public interface AppConstant {

	/**
	 * 应用版本
	 */
	String APPLICATION_VERSION = "1.0.1";

	/**
	 * 基础包
	 */
	String BASE_PACKAGES = "org.springclouddev";

	/**
	 * 应用名前缀
	 */
	String APPLICATION_NAME_PREFIX = "springcloud-";
	/**
	 * 积分模块名称
	 */
	String APPLICATION_INTEGRAL_NAME = APPLICATION_NAME_PREFIX + "integral";
	/**
	 * 网关模块名称
	 */
	String APPLICATION_GATEWAY_NAME = APPLICATION_NAME_PREFIX + "gateway";
	/**
	 * 授权模块名称
	 */
	String APPLICATION_AUTH_NAME = APPLICATION_NAME_PREFIX + "auth";
	/**
	 * 监控模块名称
	 */
	String APPLICATION_ADMIN_NAME = APPLICATION_NAME_PREFIX + "admin";
	/**
	 * 首页模块名称
	 */
	String APPLICATION_DESK_NAME = APPLICATION_NAME_PREFIX + "desk";
	/**
	 * 系统模块名称
	 */
	String APPLICATION_SYSTEM_NAME = APPLICATION_NAME_PREFIX + "system";
	/**
	 * 用户模块名称
	 */
	String APPLICATION_USER_NAME = APPLICATION_NAME_PREFIX + "user";
	/**
	 * 日志模块名称
	 */
	String APPLICATION_LOG_NAME = APPLICATION_NAME_PREFIX + "log";
	/**
	 * 开发模块名称
	 */
	String APPLICATION_DEVELOP_NAME = APPLICATION_NAME_PREFIX + "develop";
	/**
	 * 资源模块名称
	 */
	String APPLICATION_RESOURCE_NAME = APPLICATION_NAME_PREFIX + "resource";
	/**
	 * 测试模块名称
	 */
	String APPLICATION_TEST_NAME = APPLICATION_NAME_PREFIX + "test";
	/**
	 * seata 模块名称
	 */
	String APPLICATION_SEATA_STORAGE = APPLICATION_NAME_PREFIX +"seata-storage";
	/**
	 * 文件上传模块名称
	 */
	String APPLICATION_FILEUPLOAD_NAME = APPLICATION_NAME_PREFIX + "fileupload";
	/**
	 * 开发环境
	 */
	String DEV_CODE = "dev";
	/**
	 * 开发环境
	 */
	String FAT_CODE = "fat";
	/**
	 * 开发环境
	 */
	String UAT_CODE = "uat";
	/**
	 * 开发环境
	 */
	String SIT_CODE = "sit";
	/**
	 * 生产环境
	 */
	String PROD_CODE = "prod";
	/**
	 * 测试环境
	 */
	String TEST_CODE = "test";

	/**
	 * 代码部署于 linux 上，工作默认为 mac 和 Windows
	 */
	String OS_NAME_LINUX = "LINUX";
	/**
	 * seata order模块名称
	 */
	String APPLICATION_SEATA_ORDER = APPLICATION_NAME_PREFIX+"seata-order";
	/**
	 * mock server 模块名称
	 */
	String APPLICATION_MOCKSERVER_NAME = APPLICATION_NAME_PREFIX+"mock-server";
	/**
	 * hasor模块，快速开发sql接口。
	 */
	String APPLICATION_HASOR_NAME = APPLICATION_NAME_PREFIX+"hasor-server";
	/**
	 * drools 规则引擎服务
	 */
	String APPLICATION_DROOLS_NAME = APPLICATION_NAME_PREFIX+"drools-server";
}
