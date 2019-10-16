# 应用授权服务

- 启动注册中心、网关、本服务(sop-auth)
- 浏览器访问：http://localhost:8087/oauth2/appToAppAuth?app_id=2019032617262200001&redirect_uri=http%3a%2f%2flocalhost%3a8087%2foauth2callback
- 输入用户名密码登录，这里是`zhangsan/123456`

授权接口在`OAuth2Controller`中，查看回调接口在`CallbackController`中

回调接口应该由开发者实现，这里为了演示，写在一起。

token的维护，重点关注`OAuth2ManagerRedis.java`
