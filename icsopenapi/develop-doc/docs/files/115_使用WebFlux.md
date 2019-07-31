# 使用WebFlux

这里基于springboot2 + WebFlux,相关教程见:[springboot-webflux](https://www.ibm.com/developerworks/cn/java/spring5-webflux-reactive/index.html)

需要easyopen1.7.0及以上版本

- 在pom.xml中添加WebFlux依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

- 在IndexController中添加:

```java

@Controller
@RequestMapping("/api")
public class IndexController extends ApiController {
    ...
    
    // http://localhost:8080/api/mono
    @RequestMapping("mono")
    @ResponseBody
    public Mono<Object> mono(HttpServletRequest request, HttpServletResponse response) {
        return Mono.justOrEmpty(this.invoke(request, response));
    }

    ...

}
```

api的url由之前的http://localhost:8080/api变为http://localhost:8080/api/mono

其它地方不变