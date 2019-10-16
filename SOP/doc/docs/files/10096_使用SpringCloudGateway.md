# 使用SpringCloudGateway

SOP默认网关是使用Spring Cloud Zuul，您也可以切换成Spring Cloud Gateway，完整代码见`spring-cloud-gateway`分支。

如果您的系统并发量不大，建议使用zuul，因为zuul的功能更全面，有新功能会优先实现在zuul上。

- SOP中 Spring Cloud Zuul 和 Spring Cloud Gateway功能对比

| 功能 | Spring Cloud Zuul | Spring Cloud Gateway | 
| ----- | ---- | ----------------------- | 
| 签名验证|√ | √ |
| 统一异常处理|√ | √ |
| 统一返回内容|√ | √ |
| session管理|√ | √ |
| 秘钥管理|√ | √ |
| 微服务端自动验证（JSR-303）|√ | √ |
| 文件上传|√ | √ |
| 文件下载|√ | √ |
| 接口限流|√ | √ |
| 文档整合|√ | √ |
| 应用授权|√ | √ |
| 监控日志|√ | √ |
| 支持nacos|√ | √ |
| 网关动态修改参数|√ | √ |

使用Spring Cloud Gateway步骤如下：

- 打开sop-gateway/pom.xml，注释spring cloud zuul依赖，打开Spring Cloud Gateway依赖

```xml
<!-- ↓↓↓ 使用spring cloud zuul ↓↓↓
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
-->
<!-- ↑↑↑ 使用spring cloud zuul ↑↑↑ -->


<!-- ↓↓↓ 使用spring cloud gateway，处于beta阶段，推荐使用zuul ↓↓↓ -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<!-- ↑↑↑ 使用spring cloud gateway ↑↑↑ -->
```

- 打开启动类`SopGatewayApplication.java`, 注释zuul相关注解

```java
package com.gitee.sop.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

// 开启网关功能
//@EnableZuulProxy
@SpringBootApplication
public class SopGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SopGatewayApplication.class, args);
    }

}
```

- 禁用ZuulConfig类，注释掉@Configuration注解即可

```java
//@Configuration
public class ZuulConfig extends AlipayZuulConfiguration {...}
```

- 启用GatewayConfig类，打开@Configuration注释

```java
@Configuration
public class GatewayConfig extends AlipayGatewayConfiguration {...}
```

修改完毕，重启sop-gateway
