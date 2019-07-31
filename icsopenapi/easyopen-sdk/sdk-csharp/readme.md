# SDK-C#

C#对应的SDK

- EasyopenSDKCSharp： SDK源码，在此基础上添加接口
- EasyopenSDKTest：  测试用例

## 接口封装步骤

比如获取商品信息接口

- 接口名：goods.get
- 版本号：""(空字符串)
- 参数：goods_name 商品名称
- 返回信息

```
{
    "code":"0",
    "data":{
        "goods_name":"苹果iPhoneX",
        "id":1,
        "price":8000
    }
}
```

针对这个接口，封装步骤如下：


1.在`Model`文件夹下新建一个类来接收`data`部分

字段统一使用`小写字母+下划线形式`，如:name,user_age

```
namespace EasyopenSDKCSharp.Model
{
    public class Goods
    {
        public string id { get; set; }
        public string goods_name { get; set; }
        public decimal price { get; set; }
    }
}
```

2.在`Response`文件夹下新建一个返回类，继承`BaseResponse`

BaseResponse中有个泛型参数，填`Goods`类，表示返回的数据对象。

```
namespace EasyopenSDKCSharp.Response
{
    public class GetGoodsResponse : BaseResponse<EasyopenSDKCSharp.Model.Goods>
    {
    }
}
```

3.在`Request`文件夹下新建一个请求类，继承`BaseRequest`

BaseRequest中有个泛型参数，填`GetGoodsResponse`类，表示这个请求对应的返回类。
重写`GetName()`方法，填接口名。

如果要指定版本号，可重写`GetVersion()`方法，或者后续使用`request.Version = version`进行设置

```
namespace EasyopenSDKCSharp.Request
{
    public class GetGoodsRequest:BaseRequest<EasyopenSDKCSharp.Response.GetGoodsResponse>
    {
        public override string GetName()
        {
            return "goods.get";
        }     
    }
}
```



## 使用方式

```
// 接口请求
GetGoodsRequest request = new GetGoodsRequest();
// 请求参数
GoodsParam param = new GoodsParam();
// 属性赋值
param.goods_name = "iphone6";

// 设置请求参数
request.Param = param;

// 发送请求，返回结果
GetGoodsResponse response = client.Execute<GetGoodsResponse>(request);

if (response.IsSuccess())
{
    // 对应返回结果中的data
    Goods goods = response.data;
    string goods_name = goods.goods_name;
}
else
{
    throw new SystemException(response.msg);
}
```

# 使用方式2(懒人版)

如果不想添加Request,Response,Model。可以用这种方式，返回data部分是Dictionary<string, object>，后续自己处理

```
// 接口请求
CommonRequest request = new CommonRequest("goods.get");
// 请求参数
Dictionary<string, object> param = new Dictionary<string, object>();
// 属性赋值
param["goods_name"] = "iphone6";

// 设置请求参数
request.Param = param;

// 发送请求，返回结果
CommonResponse response = client.Execute<CommonResponse>(request);

if (response.IsSuccess())
{
    Assert.IsTrue(response.data is Dictionary<string, object>);
    Assert.IsTrue(response.data["goods_name"].ToString() == "苹果iPhoneX");
}
else
{
    throw new SystemException(response.msg);
}
```