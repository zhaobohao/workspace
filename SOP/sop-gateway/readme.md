# sop-gateway

网关入口，默认使用的是Spring Cloud Gateway，如果要使用Zuul，修改pom.xml

```xml
<dependency>
    <groupId>com.gitee.sop</groupId>
     <!--<artifactId>sop-bridge-gateway</artifactId>-->
     <artifactId>sop-bridge-zuul</artifactId>
    <version>version</version>
</dependency>
```