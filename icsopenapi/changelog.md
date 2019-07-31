# 更新日志

## 1.16.2

- 添加@Transaction检查
- starter支持跨域cookie

## 1.16.1 

- 修复admin样式问题
- 修复starter中mono配置问题

## 1.16.0

- 可使用`http://www.xx.com/api/接口名/版本号/`格式访问

## 1.15.2

- 修复mock问题

## 1.15.1

- 修复初始化两次问题
- starter可开启webflux服务

## 1.15.0

- devtool热部署重启产生空指针问题
- starter根据配置文件开启cors（easyopen.cors=true/false）
- starter里支持webflux
- 添加ApiContext.getApiMeta()方法

## 1.14.1

- 修复不传version导致的BUG

## 1.14.0

- 修复sdk.js
- @Api注解新增ignoreJWT()，ignoreToken()

## 1.13.0 

- 新增生成本地文档文件功能 [doc](https://durcframework.gitee.io/easyopen/#/guide?id=%E7%94%9F%E6%88%90%E6%96%87%E6%A1%A3%E6%96%87%E4%BB%B6%E5%88%B0%E6%9C%AC%E5%9C%B0%EF%BC%88v1130%EF%BC%89)
- 修复配置中心心跳检测BUG

## 1.12.6

- 修复jar包启动文档页不显示参数BUG

## 1.12.5

- 文档注解新增enumClass属性

## 1.12.4

- 调整方法调用方式

## 1.12.3

- 修复SpringCloud启动找不到ApiService问题

## 1.12.2

- 优化RedisSession

## 1.12.1

- 优化日志输出

## 1.12.0

- 配置中心【接口管理】新增接口描述字段
- 新增mock请求功能
- sdk.js优化
- 删除了自己包装的加密工具类，使用commons-codec中的工具类代替
- 优化配置中心UI

## 1.11.3

- 修复限流修改bug

## 1.11.2

- 修复单值参数BUG（cglib导致）

## 1.11.1

- 修复单值参数BUG
- 增强文档页面功能
- 支持文件下载

## 1.11.0

- 支持接口方法单值参数
- 完善文档功能
- 自定义返回结果优化
- 优化配置中心功能
- 新增vue文档界面，参见easyopen-configuration/doc-manager

## 1.10.2

- 修复SDK调用加密请求错误

## 1.10.1

- 解决listClientApi导致的NPE问题

## 1.10.0

- 新增配置中心，可管理秘钥、公私钥、权限访问，限流等功能 doc
- 对Java和C#版本的SDK进行了重构，用法更清晰代码更稳定
- 新增文档页日期选择控件，方便接口调试
- 新增文档页导出PDF功能

## 1.9.1

- 新增接口限流功能 [doc](http://durcframework.gitee.io/easyopen/#27)
- 新增文档页项目描述 [doc](http://durcframework.gitee.io/easyopen/#28)
- 支持更改文档显示顺序 [doc](http://durcframework.gitee.io/easyopen/#1410)
- 支持get方式请求
- 优化文档页传参

## 1.8.8

- 文件上传优化

## 1.8.7

- 文档页可直接上传文件
- 优化文档页数组字段提交方式

## 1.8.6

- 新增jwt过期状态码
- 文档页记住配置值
- 支持文件上传 [doc](http://durcframework.gitee.io/easyopen/#26)

## 1.8.5

- 修复ApiService类方法修饰符不是public导致无法访问bug
- 文档页面新增密码功能 [doc](http://durcframework.gitee.io/easyopen/#1406)
- 更新sdk，解决服务端随机码失效问题
- code,msg,data自定义 [doc](http://durcframework.gitee.io/easyopen/#1407)

## 1.8.4

- Controller可重写异常处理

## 1.8.3

- 可修改RedisSession存入的key前缀 [doc](http://durcframework.gitee.io/easyopen/#4000101)

## 1.8.1

- 新增监控页面 [doc](http://durcframework.gitee.io/easyopen/#25)

## 1.7.9

- 增强分布式锁稳定性

## 1.7.8

- 修复BaseLockInterceptor.getUserId()返回null导致的问题

## 1.7.7

- 新增防止表单重复提交拦截器 [doc](http://durcframework.gitee.io/easyopen/#24)

## 1.7.6

- 修复springmvc父子容器扫描时找不到接口BUG [IJR29](https://gitee.com/durcframework/easyopen/issues/IJR29)

## 1.7.5

- 新增appMode模式

## 1.7.4

- 修复文档不显示默认版本号问题
- 新增新窗口请求按钮（文档页）

## 1.7.3

- 修复oauth2获取用户bug

## 1.7.2

- 修复jwt总是能够获取bug

## 1.7.1

- 优化index.html

## 1.7.0

- 支持WebFlux

## 1.6.5 & 1.6.6

- 代码优化

## 1.6.4

- 完善注释，代码优化

## 1.6.3

- 修复配置失效问题

## 1.6.2

- 优化oauth2

## 1.6.1

- 修复文档结果显示BUG

## 1.6.0

- 新增oauth2的refreshToken功能

## 1.5.4

- 优化文档显示

## 1.5.3

- 优化ErrorFactory.getErrorMessage()

## 1.5.2

- 修复getRequest()空指针异常

## 1.5.1

- 修复RedisSession无法设置sessionTimeout问题

## 1.5.0 

- 【新增】@ApiDocMethod注解新增remark属性，方便对接口进行详细说明
- 【优化】优化doc模板

## 1.4.6~1.4.10

- 6 方法扩展
- 7 优化RedisSession
- 8 修复throw Exception前端显示问题
- 9 优化doc模板
- 10 优化RedisSessionManager


## 1.4.5

- 【新增】在ApiContext新增方法方便调用

## 1.4.2~1.4.4

- 【优化】优化doc文档

## 1.4.1

- 【修复】修复@ApiDocField注解指定elementClass失效问题

## 1.4.0

- 【新增】新增RSA+AES数据加密交互模式(数据加密传输)
- 【新增】SDK客户端支持session交互
- 【新增】可自定义默认版本号,见ApiConfig
- 【新增】新增自定义session管理,支持redis
- 【修复】修复server在有contextPath的情况下文档页面路径问题

## 1.3.1 

- 【修复】修复拦截器为空BUG

## 1.3.0

- 【新增】新增拦截器ApiInterceptor，原理同springmvc拦截器
- 【新增】签名校验新增RSA校验，校验规则见：签名算法.txt (有问题,不推荐用,1.4.0版本会修复)

## 1.2.1

- 【修复】修复cglib引起的BUG
- 【优化】添加Signer，签名算法可自定义实现

## 1.2.0

- 【新增】支持JWT
- 【新增】新增oauth2认证


## 1.0.3

- 【优化】调整文档页面样式

## 1.0.2

- 【优化】代码优化，完善注释

