# 使用eureka

## 修改网关

- 打开`/sop-gateway/pom.xml`

注释nacos相关依赖

添加eureka依赖：

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

- 打开sop-gateway下`application-dev.properties`

注释nacos配置

```properties
# nacos cloud配置
#spring.cloud.nacos.discovery.server-addr=${nacos.url}
#nacos.config.server-addr=${nacos.url}
```

添加eureka配置

```properties
# eureka注册中心
eureka.client.serviceUrl.defaultZone=http://localhost:1111/eureka/
```

## 微服务端修改

步骤同上

## admin修改

打开`application-dev.properties`，新增配置

```properties
# 网关地址，多个用逗号隔开
# 在不使用nacos时有用，使用nacos时注释掉
gateway.host=127.0.0.1:8081

# eureka注册中心
eureka.client.serviceUrl.defaultZone=http://localhost:1111/eureka/
# 如果使用eureka，填eureka，使用nacos，填eureka
registry.name=eureka
```

