## 使用说明：
| 属性名		| 类型			|  说明													|
| --------		| -----:		| :----:												|
| date			| String		|  当前日期，格式支持YYYY-MM							|
| list			| Array			|  已经签到的时间列表，格式支持YYYY-MM-DD、MM-DD、DD	|
| signin_fun	|   function	|  当天签到时调用，返回签到的日期信息					|
| repair		|    Boolean	|  是否开启补签											|
| rule			|    Boolean	|  补签规则开关，默认false，没有补签规则				|
| repair_fun	|    function	|  开启补签后，补签时调用，返回签到的日期信息			|
| choose_date	|    Boolean	|  是否可以选择日期										|
| date_change	|    function	|  当前日期改变时调用									|

``` html
<fl-signin 
	:date="date" 
	:list="list" 
	@signin_fun="signin_fun" 
	:repair="repair" 
	:rule="rule" 
	@repair_fun="repair_fun" 
	:choose_date="choose_date" 
	@date_change="date_change"
></fl-signin>
```

```javascript
data() {
	return {
		// 是否可以选择日期
		choose_date: false,
		// 当前日期
		date: "2019-03",
		// 签到成功列表
		list: ["2019-03-10", "03-15", "20"],
		// 是否开启补签
		repair: false,
		// 是否开启补签规则
		rule: false,
	}
},
methods: {
	// 签到时触发
	signin_fun(res) {
		console.log(res)
		this.list.push(res.day)
		uni.showToast({
			title: "签到成功",
			icon: "none"
		})
	},
	// 补签成功时触发
	repair_fun(res) {
		console.log(res)
		this.list.push(res.day)
		uni.showToast({
			title: "补签成功",
			icon: "none"
		})
	},
	// 当月份改变时触发
	date_change(date) {
		console.log(date);
		// 更新当前日期
		this.date = date;
		// 更新签到列表
		this.list = ["01", "02"];
	},
}
```
