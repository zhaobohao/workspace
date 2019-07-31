
# 返回结果5,外部指定
---


<table>
    <tr>
        <th>接口名</th>
        <td>doc.result.5</td>
        <th>版本号</th>
        <td></td>
    </tr>
</table>

**请求参数**

<table>
    <tr>
        <th>名称</th>
        <th>类型</th>
        <th>是否必须</th>
        <th>示例值</th>
        <th>描述</th>
    </tr>
        <tr><td>goods_name</td><td class="param-type">string</td><td><strong>是</strong></td><td>iphoneX</td><td>商品名称<br/></td></tr>
    </table>

**参数示例**

```json
{
	"goods_name":"iphoneX"
}
```

**返回结果**

<table>
    <tr>
        <th>名称</th>
        <th>类型</th>
        <th>描述</th>
    </tr>
    <tr>
        <td>code</td>
        <td>string</td>
        <td>状态值，"0"表示成功，其它都是失败</td>
    </tr>
    <tr>
        <td>msg</td>
        <td>string</td>
        <td>错误信息，出错时显示</td>
    </tr>
        <tr>
        <td>data</td>
        <td>object</td>
        <td>返回的数据，没有则返回{}
            <table>
                <tr>
                    <th>名称</th>
                    <th>类型</th>
                    <th>示例值</th>
                    <th>描述</th>
                </tr>
                                <tr><td>pageIndex</td><td>integer</td><td>1</td><td>第几页<br/></td></tr>
                                <tr><td>pageSize</td><td>integer</td><td>10</td><td>每页几条数据<br/></td></tr>
                                <tr><td>total</td><td>long</td><td>100</td><td>每页几条数据<br/></td></tr>
                                <tr><td>rows</td><td>array</td><td><table><tr><th>名称</th><th>类型</th><th>示例值</th><th>描述</th></tr><tr><td>id</td><td>long</td><td></td><td>id<br/></td></tr><tr><td>goods_name</td><td>string</td><td></td><td>商品名称<br/></td></tr><tr><td>price</td><td>float</td><td></td><td>价格<br/></td></tr></table></td><td>数据<br/></td></tr>
                            </table>
        </td>
    </tr>
    </table>

**返回示例**

```json
{
	"code":"0",
	"data":{
		"total":"100",
		"pageIndex":"1",
		"pageSize":"10",
		"rows":[
			{
				"id":""
			},
			{
				"goods_name":""
			},
			{
				"price":""
			}
		]
	},
	"msg":""
}
```


