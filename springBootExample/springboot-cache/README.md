## SpringBoot Cache 演示项目

基于注解的声明式缓存

SpringBoot 2.0的写法有些改变，参考：

https://3dot141.com/blogs/20329.html

https://my.oschina.net/u/3773384/blog/1795296

## 运行

本地安装好MySQL 5.7，并执行初始化sql脚本：`resources/sql/t_user.sql`

另外还需要安装Redis，配置好`application.yml`文件中的redis地址

测试用例：`UserServiceTest.java`

