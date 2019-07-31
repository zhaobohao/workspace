# 自定义session管理

easyopen1.4.0开始支持。

- 创建session

登陆成功后创建session，并返回sessionId

```java
// 自定义session
    @PostMapping("managedSessionLogin")
    public String managedSessionLogin(HttpServletRequest request) {
        // 假设登陆成功,创建一个sessionId返回给客户端
        SessionManager sessionManager = ApiContext.getApiConfig().getSessionManager();
        HttpSession session = sessionManager.getSession(null);
        session.setAttribute("username", "tom");
        
        return session.getId();
    }
```

- 使用session

客户端需要传递access_token参数，值为sessionId

请求参数：

```
{
	"access_token": "4191FB1D8356495D98ECCF91C615A530",
	"app_key": "test",
	"data": "{}",
	"sign": "F7AB6BC059DFCA93CA2328C9BAF236BA",
	"sign_method": "md5",
	"name": "manager.session.get",
	"format": "json",
	"version": "",
	"timestamp": "2018-03-13 13:48:45"
}
```

服务端通过HttpSession session = ApiContext.getManagedSession();获取session

```java
@Api(name = "manager.session.get")
    public Object managersetsession() {
        HttpSession session = ApiContext.getManagedSession();

        System.out.println(session.getId());
        
        Object user = session.getAttribute("username");

        return user;
    }
```

## 使用redis管理session

easyopen默认使用谷歌的guava缓存进行session管理，但是在集群的情况下会有问题，因此easyopen还提供了一个Redis版本。配置如下：

- pom添加redis依赖

```
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```

- 添加redis参数

```

#################redis基础配置#################
spring.redis.database=0
spring.redis.host=10.1.11.48
spring.redis.password=0987654321rfvujmtgbyhn
spring.redis.port=6379
# 连接超时时间 单位 ms（毫秒）
spring.redis.timeout=3000

#################redis线程池设置#################
# 连接池中的最大空闲连接，默认值也是8。
spring.redis.pool.max-idle=500
#连接池中的最小空闲连接，默认值也是0。
spring.redis.pool.min-idle=50
# 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
spring.redis.pool.max-active=2000
# 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
spring.redis.pool.max-wait=1000
```

- 设置apiConfig

```java
@Controller
@RequestMapping(value = "/api")
public class IndexController extends ApiController {
    
    @Autowired
    private RedisTemplate redisTemplate; // 1 声明redis模板

    @Override
    protected void initApiConfig(ApiConfig apiConfig) {
        // 配置秘钥键值对
        Map<String, String> appSecretStore = new HashMap<String, String>();
        appSecretStore.put("test", "123456");

        // 2 配置sessionManager
        RedisSessionManager sessionManager = new RedisSessionManager(redisTemplate);        
        apiConfig.setSessionManager(sessionManager);
        
        apiConfig.addAppSecret(appSecretStore);
    }

}
```

### 修改redis的key前缀

默认存入redis的key前缀为`session:`，如果要自定义前缀可调用：

```
sessionManager.setKeyPrefix("session-key:");
``` 

# app_key和secret存放在数据库或redis中

这里以redis为例

新建一个RedisAppSecretManager类实现AppSecretManager接口

```java
/**
 * 使用方式:
 * 
 * <pre>
@Autowired
private AppSecretManager appSecretManager;
    
@Override
protected void initApiConfig(ApiConfig apiConfig) {
    ...
    apiConfig.setAppSecretManager(appSecretManager);
    ...
}   

 * </pre>
 * 
 * @author tanghc
 *
 */
@Component
public class RedisAppSecretManager implements AppSecretManager {

    public static String APP_KEY_PREFIX = "easyopen_app_key:";
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    
    @Override
    public void addAppSecret(Map<String, String> appSecretStore) {
        stringRedisTemplate.opsForHash().putAll(APP_KEY_PREFIX, appSecretStore);
    }

    @Override
    public String getSecret(String appKey) {
        return (String)stringRedisTemplate.opsForHash().get(APP_KEY_PREFIX, appKey);
    }

    @Override
    public boolean isValidAppKey(String appKey) {
        if (appKey == null) {
            return false;
        }
        return getSecret(appKey) != null;
    }

}
```

存放app_key和secret采用hash set的方式,这样在redis中查看会比较方便,一目了然.

然后在IndexController中:

```java
@Autowired
private AppSecretManager appSecretManager;
    
@Override
protected void initApiConfig(ApiConfig apiConfig) {
    ...
    apiConfig.setAppSecretManager(appSecretManager);
    ...
}   
```