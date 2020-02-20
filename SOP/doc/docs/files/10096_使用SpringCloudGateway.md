# 使用SpringCloudGateway

修改`sop-gateway/pom.xml`配置，artifactId部分改成`sop-bridge-gateway`即可

```xml
<dependency>
    <groupId>com.gitee.sop</groupId>
     <!-- 使用zuul作为网关 -->
     <!--<artifactId>sop-bridge-zuul</artifactId>-->
     <!-- 使用spring cloud gateway作为网关 -->
     <artifactId>sop-bridge-gateway</artifactId>
    <version>对应版本</version>
</dependency>
```

修改完毕，重启sop-gateway
