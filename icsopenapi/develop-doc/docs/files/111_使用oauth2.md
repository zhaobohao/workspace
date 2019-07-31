# 使用oauth2

如果第三方应用和本开放平台对接时需要获取用户隐私数据（如商品、订单），为为了安全与隐私，第三方应用需要取得用户的授权，即获取访问用户数据的授权令牌 AccessToken 。这种情况下，第三方应用需要引导用户完成帐号“登录授权”的流程。

easyopen从1.2.0版本开始支持oauth2认证。接入方式很简单：

1. 新建一个Oauth2ManagerImpl类，实现Oauth2Manager接口
2. 用户类实现OpenUser接口。

```java
@Service
public class Oauth2ManagerImpl implements Oauth2Manager {
...
}

public class User implements OpenUser {
...
}
```

因为对于accessToken的管理每个开发人员所用的方式都不一样，所以需要自己来实现。

- Oauth2Manager接口定义如下：

```java
public interface Oauth2Manager {

    /**
     * 添加 auth code
     * 
     * @param authCode
     *            code值
     * @param authUser
     *            用户
     */
    void addAuthCode(String authCode, OpenUser authUser);

    /**
     * 添加 access token
     * 
     * @param accessToken
     *            token值
     * @param authUser
     *            用户
     * @param expiresIn 时长,秒
     */
    void addAccessToken(String accessToken, OpenUser authUser, long expiresIn);

    /**
     * 验证auth code是否有效
     * 
     * @param authCode
     * @return 无效返回false
     */
    boolean checkAuthCode(String authCode);

    /**
     * 根据auth code获取用户
     * 
     * @param authCode
     * @return 返回用户
     */
    OpenUser getUserByAuthCode(String authCode);

    /**
     * 根据access token获取用户名
     * 
     * @param accessToken
     *            token值
     * @return 返回用户
     */
    OpenUser getUserByAccessToken(String accessToken);

    /**
     * 获取auth code / access token 过期时间
     * 
     * @return
     */
    long getExpireIn(ApiConfig apiConfig);

    /**
     * 用户登录，需判断是否已经登录
     * @param request
     * @return 返回用户对象
     */
    OpenUser login(HttpServletRequest request) throws LoginErrorException;
}
```

## accessToken获取流程


- 第一步获取code

```
1、首先通过如http://localhost:8080/api/authorize?client_id=test&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Foauth2callback访问授权页面；
2、该控制器首先检查clientId是否正确；如果错误将返回相应的错误信息；
3、然后判断用户是否登录了，如果没有登录首先到登录页面登录；
4、登录成功后生成相应的code即授权码，然后重定向到客户端地址，如http://localhost:8080/oauth2callback?code=6d250650831fea227749f49a5b49ccad；在重定向到的地址中会带上code参数（授权码），接着客户端可以根据授权码去换取accessToken。
```

- 第二步通过code换取accessToken

```
1、首先通过如http://localhost:8080/api/accessToken，POST提交如下数据访问:

        code:6d250650831fea227749f49a5b49ccad
        client_id:test
        client_secret:123456
        grant_type:authorization_code
        redirect_uri:http://localhost:8080/api/authorize
       
2、服务器会验证client_id、client_secret、code的正确性，如果错误会返回相应的错误；
3、如果验证通过会生成并返回相应的访问令牌accessToken。

{
  "access_token": "01e111c0d3c8e415fea038d5c64432ef",
  "refresh_token": "d1165b75d386b3ef1bd0423b4e3bfef9",
  "token_type": "Bearer",
  "expires_in": 7200,
  "username": "admin"
}
```

以上两个步骤需要在客户端上实现。示例项目easyopen-server上有个例子可以参考，启动服务，然后访问http://localhost:8080/go_oauth2

获取accessToken用户：

```
// 拿到accessToken用户
OpenUser user = ApiContext.getAccessTokenUser();
```

## 使用refreshToken刷新accessToken

accessToken有过期时间，为了防止过期可以通过refreshToken来换取新的accessToken，方便后续接口调用。

```
1. 首先通过如http://localhost:8080/api/accessToken，POST提交如下数据访问:
        refresh_token:你的refreshToken
        client_id:test
        client_secret:123456
        grant_type:refresh_token
2. 服务器会验证client_id、client_secret、refresh_token的正确性，如果错误会返回相应的错误；
3. 如果验证通过会生成并返回新的访问令牌accessToken和新的refreshToken

返回结果：
{
  "access_token": "01e111c0d3c8e415fea038d5c64432ef",
  "refresh_token": "d1165b75d386b3ef1bd0423b4e3bfef9",
  "token_type": "Bearer",
  "expires_in": 7200,
  "username": "admin"
}

```

成功换取新的accessToken和refreshToken后，老的accessToken和refreshToken不能使用。