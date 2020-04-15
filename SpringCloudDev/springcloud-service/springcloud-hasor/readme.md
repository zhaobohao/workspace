# HASOR 

- 详细地址：https://www.hasor.net/web/dataway/for_boot.html
- Dataway 官方手册：https://www.hasor.net/web/dataway/about.html
- Dataway 在 OSC 上的项目地址，欢迎收藏：https://www.oschina.net/p/dataway
- DataQL 手册地址：https://www.hasor.net/web/dataql/what_is_dataql.html
- Hasor 项目的首页：https://www.hasor.net/web/index.html
## 应用场景
* 取数据
```
在一些 报表、看板 纯展示类的项目中。我们做到了所有接口真正的 零 开发全配置。所有取数逻辑全部通过 DataQL + SQL 的方式满足。 在此期间遇到最大的挑战是复杂查询中需要 拼SQL，随着 DataQL 查询组件的完善，这一问题被攻克。

对比往期项目对于后端技术人员的需求从 3～5 人的苦逼通宵加班，直接缩减为 1 人配置化搞定 。即便是第二天要上线新的逻辑，通过 DataQL + SQL。依然可以分分钟满足需求变更。
```
* 存数据

````
在内部某个类 ERP 项目中，20多个表单页面。每个表单页面或多或少都有直接将单据数据录入到数据库的场景，每个单据的录入逻辑都有很大的不同。 我们通过 DataQL + SQL 的方式在早期用了1000 行左右的核心代码。其它数据存取逻辑全部配置化完成。

如今随着 DataQL 工具链的完善，其中绝大部分场景可以完全配置化无需开发了。
````
* 数据聚合
````
和 GraphQL 相同，这是设计 DataQL 的初衷。将数据库和服务等多个结果进行汇聚然后返回给前端，这是 DataQL 的使命。 Dataway 是这一过程变得更加简单和高效。
````
## 启动工程
在启动日志中看到下列信息输出就表示 Dataway 已经可以正常访问了。
````
2020-04-01 09:13:18.502 [main] INFO  n.h.core.context.TemplateAppContext - loadModule class net.hasor.dataway.config.DatawayModule
2020-04-01 09:13:18.502 [main] INFO  n.hasor.dataway.config.DatawayModule - dataway api workAt /api/
2020-04-01 09:13:18.502 [main] INFO  n.h.c.e.AbstractEnvironment - var -> HASOR_DATAQL_DATAWAY_API_URL = /api/.
2020-04-01 09:13:18.515 [main] INFO  n.hasor.dataway.config.DatawayModule - dataway admin workAt /interface-ui/
````
* dataway api workAt /api/ 表示 API 的工作路径。

* dataway admin workAt /interface-ui/ 表示 管理配置界面的地址。

* 此时访问：http://<yourIP>:<yourProt>/interface-ui/ 就可以看到配置页面了。