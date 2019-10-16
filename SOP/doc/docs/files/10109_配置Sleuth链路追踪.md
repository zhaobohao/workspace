# 配置Sleuth链路追踪

配置了Sleuth可以很方便查看微服务的调用路线图，可快速定位问题。

SOP基于SpringCloud，因此只要整合[Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth)即可。
除此之外，还需要支持dubbo的链路的跟踪，Sleuth在2.0已经对dubbo做了支持，详见：[brave-instrumentation-dubbo-rpc](https://github.com/openzipkin/brave/tree/master/instrumentation/dubbo-rpc)

接入Spring Cloud Sleuth步骤如下：

- 下载zipkin服务器

以mac环境为例，执行下面命令，下载jar并启动zipkin服务

```
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar
```

默认端口是9411，更多安装方式详见：[quickstart](https://zipkin.io/pages/quickstart.html)

- sop-gateway/pom.xml添加依赖

```xml
<!--开启zipkin服务链路跟踪-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
```

配置文件新增

```properties
# zipkin服务跟踪
spring.zipkin.base-url=http://127.0.0.1:9411/
# 设置sleuth收集信息的比率，默认0.1，最大是1，数字越大越耗性能
spring.sleuth.sampler.probability=1
```
重启sop-gateway

- 打开sop-story-web/pom.xml

添加依赖：

```xml
<!--开启zipkin服务链路跟踪-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
<!-- zipkin支持dubbo -->
<dependency>
    <groupId>io.zipkin.brave</groupId>
    <artifactId>brave-instrumentation-dubbo-rpc</artifactId>
    <version>5.6.6</version>
</dependency>
```

配置文件新增：

```properties
# zipkin服务跟踪
spring.zipkin.base-url=http://127.0.0.1:9411/
# 设置sleuth收集信息的比率，默认0.1，最大是1，数字越大越耗性能
spring.sleuth.sampler.probability=1
# dubbo使用zipkin过滤器
dubbo.provider.filter=tracing
dubbo.consumer.filter=tracing
```

重启服务

- 打开sop-book/sop-book-web/pom.xml

步骤同上

- 运行DubboDemoTest.java单元测试

运行完毕看控制台，找到日志信息

```text
2019-07-18 16:22:04.438  INFO [story-service,59dae98250b276bd,60828035658f175f,true] 90553 --- [:12345-thread-2] c.g.s.s.service.DefaultDemoService       : dubbo provider, param: DemoParam(id=222)
```

日志内容多了`[story-service,59dae98250b276bd,60828035658f175f,true]`部分，这些是zipkin加进去的，说明如下：

```text
story-service：服务名称
59dae98250b276bd：traceId
60828035658f175f：spanId
true：是否上传到zipkin服务器
```

查看各个服务的控制台，可以发现traceId是一致的。

- 浏览器打开：http://127.0.0.1:9411/

将traceId复制黏贴到右上角文本框进行查询，可看到服务调用链。


![预览](images/10109_1.png "10109_1.png")



