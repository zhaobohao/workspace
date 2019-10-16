# easyopen-server

接口服务器空项目，可在此基础上做开发。

入口类：IndexController

业务类在api下，重点关注这里即可。

```
└─com.gitee.easyopen.server
                        │  EmptySpringbootApplication.java  // springboot启动类
                        │  IndexController.java             // easyopen入口类
                        │
                        ├─api   // 存放接口
                        │  │  GoodsApi.java // 示例接口
                        │  │
                        │  ├─param // 存放接口参数类
                        │  │      GoodsParam.java
                        │  │
                        │  └─result // 存放放回接口类
                        │          Goods.java
                        │
                        ├─config  // spring配置
                        │      CorsConfig.java
                        │
                        ├─interceptor // easyopen拦截器
                        │      LogInterceptor.java
                        │
                        └─message // 错误处理
                                CommonErrors.java
```