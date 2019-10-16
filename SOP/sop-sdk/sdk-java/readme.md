# sdk-java


sdk for java

SDK只依赖了三个jar包

- okhttp.jar 用于网络请求
- fastjson.jar 用于json处理
- commons-logging.jar 日志处理

## 接口封装步骤

比如获取故事信息接口

- 接口名：alipay.story.find
- 版本号：1.0
- 参数：name 故事名称
- 返回信息

```
{
	"alipay_story_find_response": {
		"msg": "Success",
		"code": "10000",
		"name": "白雪公主",
		"id": 1,
		"gmtCreate": 1554193987378
	},
	"sign": "xxxxx"
}
```

针对这个接口，封装步骤如下：

1.在`model`包下新建一个类，定义业务参数


```java
@Data
public class GetStoryModel {

    @JSONField(name = "name")
    private String name;
}
```

2.在`response`包下新建一个返回类GetStoryResponse，继承`BaseResponse`

里面填写返回的字段

```
@Data
public class GetStoryResponse extends BaseResponse {
    private Long id;
    private String name;
    private Date gmtCreate;
}
```

3.在`request`包下新建一个请求类，继承`BaseRequest`

BaseRequest中有个泛型参数，填`GetStoryResponse`类，表示这个请求对应的返回类。
重写`method()`方法，填接口名。

如果要指定版本号，可重写`version()`方法，或者后续使用`request.setVersion(version)`进行设置

```java
public class GetStoryRequest extends BaseRequest<GetStoryResponse> {
    @Override
    protected String method() {
        return "alipay.story.find";
    }
}

```

## 使用方式

```java
String url = "http://localhost:8081";
String appId = "2019032617262200001";
String privateKey = "你的私钥";

// 声明一个就行
OpenClient client = new OpenClient(url, appId, privateKey);

// 标准用法
@Test
public void testGet() {
    // 创建请求对象
    GetStoryRequest request = new GetStoryRequest();
    // 请求参数
    GetStoryModel model = new GetStoryModel();
    model.setName("白雪公主");
    
    request.setBizModel(model);

    // 发送请求
    GetStoryResponse response = client.execute(request);

    if (response.isSuccess()) {
        // 返回结果
        System.out.println(response);
    } else {
        System.out.println(response);
    }
}
```

## 使用方式2(懒人版)

如果不想添加Request,Response,Model。可以用这种方式，返回body部分是字符串，后续自己处理

body对应的是alipay_story_find_response部分

```java
@Test
public void testLazy() {
    // 创建请求对象
    CommonRequest request = new CommonRequest("alipay.story.find");
    // 请求参数
    Map<String, Object> bizModel = new HashMap<>();
    bizModel.put("name", "白雪公主");
    request.setBizModel(bizModel);

    // 发送请求
    CommonResponse response = client.execute(request);

    if (response.isSuccess()) {
        // 返回结果，body对应的是alipay_story_find_response部分
        String body = response.getBody();
        JSONObject jsonObject = JSON.parseObject(body);
        System.out.println(jsonObject);
    } else {
        System.out.println(response);
    }
}
```