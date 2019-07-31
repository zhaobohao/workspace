# easyopen-sdk-javascript

开放平台对应的sdk，可适用于前端页面

```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>sdk</title>
<script type="text/javascript" src="sdk.js"></script>
<script type="text/javascript">
//需要发布到服务器上运行，并且server端需要处理跨域
//在IndexController.java上加@CrossOrigin(origins={"*"})

sdk.config({
    url : 'http://localhost:8080/api'
    ,app_key : 'test'
    ,secret : '123456'
});

sdk.post({
    name   : 'goods.get' // 接口名
//  ,version:'1.0'
//  ,access_token:''
    ,data  : {'goods_name':'iphone'} // 请求参数
    ,callback:function(resp) { // 成功回调
        console.log(resp)
    }
});
</script>
</head>
<body>

</body>
</html>
```