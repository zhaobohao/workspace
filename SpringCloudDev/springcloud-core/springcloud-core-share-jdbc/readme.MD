#使用说明
在使用share-sphere的时候,
只需要把这个项目的application.yml配置文件调整好，在你的具体项目中引入 即可，
* 还要在 application.java的类上加上
````
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
````

* 官方文档地址
````
https://shardingsphere.apache.org/document/current/cn/manual/sharding-jdbc/usage/read-write-splitting/
````
