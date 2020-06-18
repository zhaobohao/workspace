# rbac
基于springboot写的RBAC权限管理Demo系统

演示地址：http://116.196.66.248:8090/   超级管理员账号:admin 密码:123456  

怎么演示？可以创建一个用户，绑定角色，然后对角色进行不同的授权。再以用户的账号登陆，然后验证用户的权限正确与否

建议使用IDEA导入项目,导入项目后。在config/application.properties中配置下数据源即可。。

数据库文件也给了。。在sql/rbac.sql..

关于此系统,如有不足，希望大家能不胜指点！


# 2018-3-28 补充

最近在弄hadoop。。由于服务器性能有限，所以将上面的项目和数据库全都关掉了。。暂时有一段时间是访问不了这个Demo!

不便之处，敬请谅解！

# 2019-4-17 修改

在之前做的一个项目，里面完整的实现了一套权限管理模块，觉得还不错，就将rbac重构了一下。

下面是新版rbac的几个特性：

1. 菜单和资源合并，统一管理
2. 支持精确到按钮级别的权限控制
3. 使用权限注解标注controller层方法，统一进行权限拦截
4. 权限注解支持权限依赖。比如获取用户详情接口的权限 可以绑定到修改用户详情接口的权限上。因为想要修改，肯定得先查询详情。
5. 权限url兼容rest风格的接口， 默认使用接口的请求地址，在拼上请求方法的类型。如:/api/user/1{get}、/api/user/update{post}

名词解释：

权限url：也可指资源url，菜单url。用来标识某个资源对应的链接。