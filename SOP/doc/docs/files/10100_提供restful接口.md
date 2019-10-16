# 传统web开发

默认情况下SOP只提供开放接口，也可以同时提供restful接口，即程序提供一部分的开放接口，同时提供一部分restful接口。

默认情况下提供restful功能是关闭的，开启方式如下：

- 打开sop-gateway配置文件，新增一行配置：

```properties
# 提供restful接口
sop.restful.enable=true
```

- 前端app请求网关(`2.4.1之后有变动`)

请求格式：

**2.4.1版本之前：** `http://ip:port/rest/your_path`，其中`http://ip:port/rest/`为固定部分，后面跟微服务请求路径。

**2.4.1之后：** `http://ip:port/rest/服务id/your_path`，其中`http://ip:port/rest/`为固定部分，后面跟微服务请求路径。

**注意，`2.4.1`开始多了一个服务id作为区分，这样做是为了避免各微服务之间url冲突，假如两个微服务都有一个叫`/getItems`这样的接口
那么调用`http://ip:port/rest/getItems`接口网关无法做出正确的路由，虽然可以在代码上进行规范，为了防止万一，还是强行加上了，避免采坑
。可以指定`sop.restful.old-model=true`强制使用老的调用方式**

> 可在微服务端指定一个配置：`sop.restful.prefix=xxx`。请求路径将变成：`http://ip:port/rest/xxx/your_path`

下面是一个微服务的接口例子

```java
@RestController
@RequestMapping("food")
public class TraditionalWebappController {
    @RequestMapping(value = "getFoodById", method = RequestMethod.GET)
    public Food getFoodById(Integer id) {
        Food food = new Food();
        food.setId(id);
        food.setName("香蕉");
        food.setPrice(new BigDecimal(20.00));
        return food;
    }
}
```

这是一个`食品服务`例子，serviceId为`food-service`，假设网关ip为10.0.1.11，端口8081；食品服务ip为10.0.1.22，端口2222

1. 网关访问：`http://10.0.1.11:8081/rest/food-service/food/getFoodById?id=2`

2. 本地访问：`http://10.0.1.22:2222/food/getFoodById/?id=2`

更多例子，可查看源码类：`TraditionalWebappController.java`

由此可见，对于前端调用者来说，它把网关看做一个大服务，只访问网关提供的请求，不需要关心网关后面的路由转发。网关后面各个微服务独自管理，
微服务之间的调用可以使用dubbo或feign，有了版本号的管理，可以做到服务的平滑升级，对用户来说都是无感知的。结合SOP-Admin提供的上下线功能，
可实现预发布环境功能。

- 封装请求工具【可选】

封装请求，方便调用，针对vue的封装如下：

```js
import axios from 'axios'

// 创建axios实例
const client = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 5000, // 请求超时时间
  headers: { 'Content-Type': 'application/json' }
})

const RequestUtil = {
  /**
   * 请求接口
   * @param url 请求路径，如http://localhost:8081/rest/food-service/food/getFoodById
   * @param data 请求数据，json格式
   * @param callback 成功回调
   * @param errorCallback 失败回调
   */
  post: function(url, data, callback, errorCallback) {
    client.post(url, data)
        .then(function(response) {
        const resp = response.result
        const code = resp.code
        // 成功，网关正常且业务正常
        if (code === '10000' && !resp.sub_code) {
          callback(resp)
        } else {
          // 报错
          Message({
            message: resp.msg,
            type: 'error',
            duration: 5 * 1000
          })
        }
      })
      .catch(function(error) {
        console.log('err' + error) // for debug
        errorCallback && errorCallback(error)
      })
  }
}

export default RequestUtil
```

jQuery版本如下：

```js
var RequestUtil = {
    /**
     * 请求接口
     * @param url 请求路径，如http://localhost:8081/rest/food-service/food/getFoodById
     * @param data 请求数据，json格式
     * @param callback 成功回调
     * @param errorCallback 失败回调
     */
    post: function(url, data, callback, errorCallback) {
        $.ajax({
            url: 'http://localhost:8081' // 网关url
            , type: 'post'
            , headers: { 'Content-Type': 'application/json' }
            , data: data
            ,success:function(response) {
                var resp = response.result
                var code = resp.code
                // 成功，网关正常且业务正常
                if (code === '10000' && !resp.sub_code) {
                    callback(resp)
                } else {
                    // 报错
                    alert(resp.msg);
                }
            }
            , error: function(error) {
                errorCallback && errorCallback(error)
            }
        });
    }
}
```

jQuery调用示例：

```js
$(function () {
    var data = {
        id: 1
        ,name: '葫芦娃'
    }
    RequestUtil.post('http://localhost:8081/rest/food-service/food/getFoodById', data, function (result) {
        console.log(result)
    });
})
```
