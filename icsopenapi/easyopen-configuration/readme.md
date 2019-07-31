# 配置中心

配置中心提供的功能

- 接入方管理：统一管理appKey、secret、公私钥
- 权限管理：对某些接入方开放部分接口
- 限流管理：配置接口限流功能（如秒杀场景可用到）

配置中心与接口应用分离，它们的交互方式采用Netty长连接保持通讯，并支持断开重连。交互图：

![配置中心交互图](https://images.gitee.com/uploads/images/2018/0728/094853_5bc4169d_332975.png "QQ截图20180728094824.png")

## 配置中心使用方法

### 启动服务端（easyopen-config）

- 导入apiconf-demo.sql脚本
- 配置application.properties
- 启动后访问：http://localhost:8070/
- 用户名密码:admin 123456

**注意：** 建议先启动配置中心，再启动server接口应用。如果server先启动，发现配置中心没启动，会尝试加载本地配置，如果本地没有配置就启动失败。必须要正确连上配置中心一次后，下载配置文件到本地，之后启动顺序可随意。
    
### 启动前端页面（vue项目）

- 前提：先安装好npm，[npm安装教程](https://blog.csdn.net/zhangwenwu2/article/details/52778521)。建议使用淘宝镜像。
- 打开src/utils/ApiUtil.js，修改url


然后打开命令行窗口，进入到config-manager目录，输入下面命令

```bash
# 初始化
cnpm install

# 启动，随后出现一个url，直接复制到浏览器即可
cnpm run dev
```

## 接口应用项目配置（server）

**注意：**这里的配置是server接口应用，**不是**配置中心。

配置中心启动完毕后，需要有接口应用连接过来。

- 注意：这里的配置说明是接口应用，即demo下面的应用，不是配置中心

接口应用使用配置中心步骤如下：

打开`IndexController.java`

- 去掉默认的appKey，Secret配置

去掉下面这个配置

~~Map<String, String> appSecretStore = new HashMap<String, String>();~~
~~appSecretStore.put("test", "123456");~~
~~apiConfig.addAppSecret(appSecretStore);~~

- 去掉已有的限流配置

~~apiConfig.setLimitManager(new ApiLimitManager(redisTemplate));~~

- 添加下面配置代码

```
// 配置拦截器
apiConfig.setInterceptors(new ApiInterceptor[] {
        // 限流拦截器（配置中心）
        new LimitInterceptor() ,
        // 权限拦截器（配置中心）
        new PermissionInterceptor()
        
        ... 其它拦截器
});

/* -----------------配置中心------------------ */
/*
 *  appName 应用名称，应用名称，不能重复
 *  host    配置中心ip，config服务器ip
 *  port    配置中心端口，对应config中的netty.server.port
 */
ConfigClient configClient = new ConfigClient("app1", "localhost", 8071);
/*
如果要使用分布式业务限流，使用下面这句。默认是单机限流
configClient.setLimitManager(new ApiLimitManager(redisTemplate, new ApiLimitConfigLocalManager()));
*/
apiConfig.setConfigClient(configClient);
/* -------------------------------------- */
```

配置完先启动配置中心，在启动server程序