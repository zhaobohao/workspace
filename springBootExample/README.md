## SpringBoot 


每个子项目都会使用最小依赖，大家拿来即可使用，自己可以根据业务需求自由组合搭配不同的技术构建项目。

## 环境

* JDK 1.8
* Maven latest
* Spring Boot 2.1.8
* Intellij IDEA
* mysql 5.7
* mongodb
* git 版本管理
* nginx 反向代理
* redis 缓存
* rabbitmq 消息队列

## 运行

每个子项目都可以单独运行，都是打包成jar包后，通过使用内置jetty容器执行，有3种方式运行。:

1. 在IDEA里面直接运行Application.java的main函数。
2. 另一种方式是执行`mvn clean package`命令后传到linux服务器上面，通过命令`java -Xms64m -Xmx1024m -jar xxx.jar`方式运行
3. 在linux服务器上面，配置好jdk、maven、git命令后，通过`git clone sb-xxx`拉取工程后，执行`./run.sh start test`命令来执行

注：每个子项目有自己的README.md文件，告诉你该怎么初始化环境，比如准备好数据库SQL文件等。

另外，如果你需要打包成war包放到tomcat容器中运行，可修改pom.xml文件，将打包类型从jar改成war，打包后再放到容器中运行：

``` xml
<modelVersion>4.0.0</modelVersion>
<artifactId>springboot-cache</artifactId>
<packaging>war</packaging>
```

## 后续计划

* 集成OAuth2认证

