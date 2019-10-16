# sdk-csharp

C#对应的SDK，由于本人使用mac开发，因此使用了.Net Core

测试用例在`Program.cs`


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

1.在`Model`包下新建一个类，定义业务参数

```
namespace SDKCSharp.Model
{
    public class GetStoryModel
    {
        /// <summary>
        /// 故事名称
        /// </summary>
        /// <value>The name.</value>
        [JsonProperty("name")]
        public string Name { get; set; }
    }
}
```

`[JsonProperty("name")]`是Newtonsoft.Json组件中的类，用于Json序列化，括号中的是参数名称。
类似于Java中的注解，`@JSONField(name = "xx")`

2.在`Response`包下新建一个返回类GetStoryResponse，继承`BaseResponse`

里面填写返回的字段

```
namespace SDKCSharp.Response
{
    public class GetStoryResponse: BaseResponse
    {
        [JsonProperty("id")]
        public int Id { get; set; }

        [JsonProperty("name")]
        public string Name { get; set; }

        [JsonProperty("gmt_create")]
        public string GmtCreate { get; set; }

    }
}

```

3.在`Request`文件夹下新建一个请求类，继承`BaseRequest`

BaseRequest中有个泛型参数，填`GetStoryResponse`类，表示这个请求对应的返回类。
重写`GetMethod()`方法，填接口名。

如果要指定版本号，可重写`GetVersion()`方法，或者后续使用`request.Version = version`进行设置

```
namespace SDKCSharp.Request
{
    public class GetStoryRequest : BaseRequest<GetStoryResponse>
    {
        public override string GetMethod()
        {
            return "alipay.story.find";
        }
    }

}
```



## 使用方式

```
class MainClass
{
    static string url = "http://localhost:8081";
    static string appId = "2019032617262200001";
    // 平台提供的私钥
    static string privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCXJv1pQFqWNA/++OYEV7WYXwexZK/J8LY1OWlP9X0T6wHFOvxNKRvMkJ5544SbgsJpVcvRDPrcxmhPbi/sAhdO4x2PiPKIz9Yni2OtYCCeaiE056B+e1O2jXoLeXbfi9fPivJZkxH/tb4xfLkH3bA8ZAQnQsoXA0SguykMRZntF0TndUfvDrLqwhlR8r5iRdZLB6F8o8qXH6UPDfNEnf/K8wX5T4EB1b8x8QJ7Ua4GcIUqeUxGHdQpzNbJdaQvoi06lgccmL+PHzminkFYON7alj1CjDN833j7QMHdPtS9l7B67fOU/p2LAAkPMtoVBfxQt9aFj7B8rEhGCz02iJIBAgMBAAECggEARqOuIpY0v6WtJBfmR3lGIOOokLrhfJrGTLF8CiZMQha+SRJ7/wOLPlsH9SbjPlopyViTXCuYwbzn2tdABigkBHYXxpDV6CJZjzmRZ+FY3S/0POlTFElGojYUJ3CooWiVfyUMhdg5vSuOq0oCny53woFrf32zPHYGiKdvU5Djku1onbDU0Lw8w+5tguuEZ76kZ/lUcccGy5978FFmYpzY/65RHCpvLiLqYyWTtaNT1aQ/9pw4jX9HO9NfdJ9gYFK8r/2f36ZE4hxluAfeOXQfRC/WhPmiw/ReUhxPznG/WgKaa/OaRtAx3inbQ+JuCND7uuKeRe4osP2jLPHPP6AUwQKBgQDUNu3BkLoKaimjGOjCTAwtp71g1oo+k5/uEInAo7lyEwpV0EuUMwLA/HCqUgR4K9pyYV+Oyb8d6f0+Hz0BMD92I2pqlXrD7xV2WzDvyXM3s63NvorRooKcyfd9i6ccMjAyTR2qfLkxv0hlbBbsPHz4BbU63xhTJp3Ghi0/ey/1HQKBgQC2VsgqC6ykfSidZUNLmQZe3J0p/Qf9VLkfrQ+xaHapOs6AzDU2H2osuysqXTLJHsGfrwVaTs00ER2z8ljTJPBUtNtOLrwNRlvgdnzyVAKHfOgDBGwJgiwpeE9voB1oAV/mXqSaUWNnuwlOIhvQEBwekqNyWvhLqC7nCAIhj3yvNQKBgQCqYbeec56LAhWP903Zwcj9VvG7sESqXUhIkUqoOkuIBTWFFIm54QLTA1tJxDQGb98heoCIWf5x/A3xNI98RsqNBX5JON6qNWjb7/dobitti3t99v/ptDp9u8JTMC7penoryLKK0Ty3bkan95Kn9SC42YxaSghzqkt+uvfVQgiNGQKBgGxU6P2aDAt6VNwWosHSe+d2WWXt8IZBhO9d6dn0f7ORvcjmCqNKTNGgrkewMZEuVcliueJquR47IROdY8qmwqcBAN7Vg2K7r7CPlTKAWTRYMJxCT1Hi5gwJb+CZF3+IeYqsJk2NF2s0w5WJTE70k1BSvQsfIzAIDz2yE1oPHvwVAoGAA6e+xQkVH4fMEph55RJIZ5goI4Y76BSvt2N5OKZKd4HtaV+eIhM3SDsVYRLIm9ZquJHMiZQGyUGnsvrKL6AAVNK7eQZCRDk9KQz+0GKOGqku0nOZjUbAu6A2/vtXAaAuFSFx1rUQVVjFulLexkXR3KcztL1Qu2k5pB6Si0K/uwQ=";


    // 声明一个就行
    static OpenClient client = new OpenClient(url, appId, privateKey);

    public static void Main(string[] args)
    {
        TestGet();
    }

    // 标准用法
    private static void TestGet()
    {
        // 创建请求对象
        GetStoryRequest request = new GetStoryRequest();
        // 请求参数
        GetStoryModel model = new GetStoryModel();
        model.Name = "白雪公主";
        request.BizModel = model;

        // 发送请求
        GetStoryResponse response = client.Execute(request);

        if (response.IsSuccess())
        {
            // 返回结果
            Console.WriteLine("成功！response:{0}\n响应原始内容:{1}", JsonUtil.ToJSONString(response), response.Body);
        }
        else
        {
            Console.WriteLine("错误, code:{0}, msg:{1}, subCode:{2}, subMsg:{3}",
                response.Code, response.Msg, response.SubCode, response.SubMsg);
        }
    }

    
}
```

## 使用方式2(懒人版)

如果不想添加Request,Response,Model。可以用这种方式，返回data部分是Dictionary<string, object>，后续自己处理

```
// 懒人版，如果不想添加Request,Response,Model。可以用这种方式，返回Dictionary<string, object>，后续自己处理
private static void TestCommon()
{
    // 创建请求对象
    CommonRequest request = new CommonRequest("alipay.story.find");
    // 请求参数
    Dictionary<string, string> bizModel = new Dictionary<string, string>
    {
        ["name"] = "白雪公主"
    };

    request.BizModel = bizModel;

    // 发送请求
    CommonResponse response = client.Execute(request);

    if (response.IsSuccess())
    {
        // 返回结果
        string body = response.Body;
        Dictionary<string, object> dict = JsonUtil.ParseToDictionary(body);
        Console.WriteLine(dict.ToString());
    }
    else
    {
        Console.WriteLine("错误, code:{0}, msg:{1}, subCode:{2}, subMsg:{3}",
            response.Code, response.Msg, response.SubCode, response.SubMsg);
    }
}
```