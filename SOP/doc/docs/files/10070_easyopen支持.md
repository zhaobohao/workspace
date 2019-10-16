# easyopen支持

SOP对easyopen项目提供了很好的支持，如果您的服务端使用了easyopen框架，相关配置步骤如下：

## 服务端配置

首先是服务端相关配置

- pom添加依赖

```xml
<!-- sop接入依赖 -->
		<dependency>
			<groupId>com.gitee.sop</groupId>
			<artifactId>sop-service-common</artifactId>
			<version>最新版本</version>
		</dependency>

		<!-- 使用nacos注册中心
            版本 0.2.x.RELEASE 对应的是 Spring Boot 2.x 版本，版本 0.1.x.RELEASE 对应的是 Spring Boot 1.x 版本。
           https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-alibaba-nacos-discovery
        -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
			<version>0.2.2.RELEASE</version>
			<exclusions>
				<exclusion>
					<groupId>com.alibaba.nacos</groupId>
					<artifactId>nacos-client</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		<dependency>
			<groupId>com.alibaba.nacos</groupId>
			<artifactId>nacos-client</artifactId>
			<version>1.1.0</version>
		</dependency>

		<dependency>
			<groupId>net.oschina.durcframework</groupId>
			<artifactId>easyopen</artifactId>
			<version>1.16.1</version>
		</dependency>
        <!-- sop接入依赖 end -->
```

easyopen版本必须升级到1.16.1

- 启动类上面添加注解@EnableDiscoveryClient，将自己注册到注册中心
- 新增一个配置类，继承EasyopenServiceConfiguration，内容为空

```java
@Configuration
public class SopConfig extends EasyopenServiceConfiguration {
}
```

服务端配置完毕，重启服务。

## 网关端配置

接下来是网关的配置

- 打开ZuulConfig.java，注释掉原本的@Configuration，新增如下Configuration

```java
@Configuration
public class ZuulConfig extends EasyopenZuulConfiguration {

}
```

配置完毕，重启网关服务，可运行测试用例`EasyopenClientPostTest.java`验证

**注：** 配置完成后easyopen签名校验将会关闭，改用网关端来校验；网关对easyopen返回的结果不进行处理，直接返回服务端的结果。

完整配置可查看sop-example/sop-easyopen项目
