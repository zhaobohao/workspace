# changelog

## 2.4.1

- 优化restful接口调用（如果正在使用此功能，必看 [doc](https://durcframework.gitee.io/sop/#/files/10100_%E6%8F%90%E4%BE%9Brestful%E6%8E%A5%E5%8F%A3?t=1571107529449)）

## 2.4.0

- 支持自定义限流持续时间（每n秒允许m个请求，需要执行`sop-2.4.0.sql`升级脚本）

## 2.3.2

- 支持spring cloud gateway下restful接口调用

## 2.3.1

- 修复restful接口调用通配符问题

## 2.3.0

- 支持请求restful接口（设置`sop.restful.enable=true`）

## 2.2.0（需要执行`sop-2.2.0.sql`升级文件）

- 支持eureka注册中心，见`eureka`分支
- 签名内容支持urlencode（设置`sign.urlencode=true`）
- 可扩展其它注册中心

## 2.1.3

- 优化文件上传校验

## 2.1.2

- 优化获取路由配置

## 2.1.1

- 修复springmvc获取路由问题

## 2.1.0

- 支持分布式限流（redis实现）
- 可调整JSR-303校验顺序
- 修复springmvc工程注册到nacos无法读取路由配置问题

## 2.0.0

- 全面使用nacos，舍弃zookeeper（1.x版本见1.x分支）
- 可自定义文档模块显示顺序

## 1.15.2

- 优化SpringCloudGateway上传文件功能
- 优化SpringCloudGateway动态修改参数功能

## 1.15.1

- 修复未配置正确MessageConverter导致的异常

## 1.15.0

- 优化预发布、灰度
- 网关动态修改请求参数
- 支持swagger-bootstrap插件
- 优化admin服务列表显示
- 优化文档刷新逻辑
- 新增测试all in one
- 修复中文乱码问题

## 1.14.0

- 支持预发布、灰度发布环境

## 1.13.7

- 修复修复context-path识别问题

## 1.13.6

- 修复@RequestBody不能绑定问题

## 1.13.5

- 修复postJson下version获取不到问题

## 1.13.4

- 修复admin服务列表最后更新时间不显示问题
- 优化上传路由配置逻辑
- 微服务可获得access_token, notify_url参数

## 1.13.3

- 优化参数绑定

## 1.13.2

- 修复json方式请求获取不到参数问题
- 微服务端新增获取开放平台请求参数

## 1.13.1

- 支持json方式请求（application/json）
- 支持传统web服务开发（见文档`传统web开发`）

## 1.13.0

- 新增IP黑名单

## 1.12.4

- 优化属性文件配置
- 新增sleuth接入文档
- admin的isv列表新增备注字段

## 1.12.3

- 修复删除zk节点导致的BUG

## 1.12.2

- 沙盒支持文件上传

## 1.12.1

- 修复重启网关路由状态重置BUG
- 优化SpringCloudGateway

## 1.12.0

- admin后台新增角色管理
- 支持nacos作为注册中心

## 1.11.0

- 秘钥管理改造
- 服务端返回sign
- 新增SDK返回sign处理
- 新增沙箱环境

## 1.10.0

- 新增监控日志

## 1.9.0

- 改造限流
- 增强参数绑定

## 1.8.0

- 支持文件上传

## 1.7.2

- 修复微服务参数绑定BUG
- Admin新增vue界面

## 1.7.1

- 支持接口名版本号放在url后面

## 1.7.0

- 可自定义数据节点名称

## 1.6.0

- 新增应用授权

## 1.5.0

- admin新增signType字段
- 修复easyopen接入无法访问BUG

## 1.4.0

- 新增文档分组显示
- 支持easyopen文档注解
- BUG修复

## 1.3.0

- 新增接口限流功能 [doc](http://durcframework.gitee.io/sop/#/files/10092_%E6%8E%A5%E5%8F%A3%E9%99%90%E6%B5%81?t=1555378655699)
- 新增文档整合功能 [doc](http://durcframework.gitee.io/sop/#/files/10041_%E7%BC%96%E5%86%99%E6%96%87%E6%A1%A3?t=1555378655698)
- 新增springmvc项目接入demo

## 1.2.0

- SOP Admin新增用户登录
- 新增基础SDK(Java,C#) [doc](http://durcframework.gitee.io/sop/#/files/10095_SDK%E5%BC%80%E5%8F%91?t=1554693919597)

## 1.1.0

- 新增ISV管理 [doc](http://durcframework.gitee.io/sop/#/files/10085_ISV%E7%AE%A1%E7%90%86?t=1554123435621)
- 新增接口授权 [doc](http://durcframework.gitee.io/sop/#/files/10090_%E8%B7%AF%E7%94%B1%E6%8E%88%E6%9D%83?t=1554123435621)

## 1.0.0

- 第一次发布
