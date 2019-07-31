# Hystrix扩展

## 使用方式

- 将该项目`mvn deploy`到maven私服
- 接口项目依赖：

```xml
<dependency>
    <groupId>net.oschina.durcframework</groupId>
    <artifactId>easyopen-ext-hystrix</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

- 接口方法添加@ApiHystrix注解

```java
@Api(name = "goods.get")
@ApiDocMethod(description="获取商品")
@ApiHystrix(executionTimeoutInMilliseconds = 500, errorCode = "333", errorMsg = "超时了。")
public Goods getGoods(GoodsParam param) {
    Goods goods = new Goods();
    goods.setId(1L);
    goods.setGoods_name("苹果iPhoneX");
    goods.setPrice(new BigDecimal(8000));
    try {
        Thread.sleep(600);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return goods;
}
```

可查看easyopen-server-ext项目

## 添加@ApiHystrix属性

如果要添加属性，

- 先在@ApiHystrix中定义好
- 找到com.gitee.easyopen.ext.hystrix.HystrixApiRegistEvent.buildSetter方法，在方法中追加即可
