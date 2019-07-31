/**
请求示例：

var name = 'goods.get';
var version = '';

var jsonData = {};
 // form表单
 var formArray = _parent.find('form').serializeArray();

 jQuery.each(formArray, function(i, field){
     jsonData[field.name] = field.value;
 });

 ApiUtil.post(name,jsonData,version,function(resp,postDataStr){
     console.log(resp);
 });
*/

import sdk from '@/utils/sdk'
import { Message } from 'element-ui'
import router from '@/router/index'

var ApiUtil = (function(){
	var params = {};
	var JWT_KEY = "easyconf_jwt";

  // 测试环境
	// var url = 'http://bbb.a.com:8070/api';
  	var url = process.env.BASE_API 
  
	var app_key = 'test';
	var secret = '123456';

    (function() {
    	var aPairs, aTmp;
        var queryString = window.location.search.toString();
        queryString = queryString.substr(1, queryString.length); //remove   "?"
        aPairs = queryString.split("&");
        for (var i = 0; i < aPairs.length; i++) {
            aTmp = aPairs[i].split("=");
            params[aTmp[0]] = aTmp[1];
        }
    })();

    /* **************私有方法************** */
    function getJwt() {
		let jwt = localStorage.getItem(JWT_KEY)
		// console.log('aaaaaa',jwt)
    	return jwt || '';
    }

  //Html编码获取Html转义实体
	function htmlEncode(value){
		return $('<div/>').text(value).html();
	}
	//Html解码获取Html实体
	function htmlDecode(value){
		return $('<div/>').html(value).text();
	}

	function add0(m){return m<10?'0'+m:m }


    /* ************************************* */
    return {
    	post:function(name,data,callback) {
			if(!localStorage.getItem(JWT_KEY)){
				ApiUtil.logout()
			}
			sdk.config({
					url : url
				    ,app_key : app_key
				    ,secret : secret
				    ,jwt : getJwt()
				});
    		sdk.post({
    			name   : name // 接口名
				,data  : data // 请求参数
				// ,jwt : getJwt()
				,callback:function(resp,postDataStr) { // 成功回调
					console.log('resp',resp)
					var code = resp.code;	
					if(!code || code == '-9') {
						Message.error('系统错误')
						return
					}
					if(code == '-100' || code == '18' || code == '21') { // 未登录
						ApiUtil.logout();
						return;
					}
					if(code == '0') { // 成功
						callback(resp, postDataStr);
					}else {
						Message.error(resp.msg)
					}
    			}
    		});
         }
      // get:function(name,callback) {
      //   sdk.get({
      //     name   : name // 接口名
      //     ,callback:function(resp,getDataStr) { // 成功回调
      //       var code = resp.code;
      //       if(!code || code == '-9') {
      //         MsgUtil.topMsg('系统错误');
      //         return;
      //       }
      //       if(code == '-100' || code == '18' || code == '21') { // 未登录
      //         ApiUtil.logout();
      //         return;
      //       }
      //       if(code == '0') { // 成功
      //         callback(resp,getDataStr);
      //       } else {
      //         MsgUtil.topMsg(resp.msg);
      //       }
      //     }
      //   });
      // }
    	// ,logout:function() {
        //     this.post('nologin.admin.logout',{},function (resp) {
		// 		localStorage.removeItem("easyconf_jwt");
        //         router.push('/login')
        //     })
		// }
		,JWT_KEY:JWT_KEY
		,logout:function() {
			localStorage.clear()
			router.push('/login')	
    	}
    	,getParam:function(key) {
    		return params[key];
    	}
    	,htmlEncode:function(value) {
    		return htmlEncode(value);
    	}
    	,htmlDecode:function(value) {
    		return htmlDecode(value);
    	}
    	,getUrl:function() {
    		return url;
    	}
    };
})();

export default ApiUtil

