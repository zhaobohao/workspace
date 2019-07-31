import {sdk} from '@/utils/sdk'
import { Message } from 'element-ui'
import router from '@/router/index'
import store from '@/store/index'

var ApiUtil = (function(){
	var params = {};
	var JWT_KEY = "easyconf_jwt";

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
	// 获取jwt
    function getJwt() {
		let jwt = sessionStorage.getItem(JWT_KEY)
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

    return {
    	post:function(name,data,version,form,callback) {
			sdk.config({
					url : sessionStorage.getItem('url')
				    ,app_key : sessionStorage.getItem('app_key') || 'test'
				    ,secret : sessionStorage.getItem('secret') || '123456'
				    ,jwt : getJwt()
				});
				// opts
    		sdk.post({
    			name   : name // 接口名
				,data  : data // 请求参数
				,version : version
				,form : form
				,callback:function(resp,postDataStr) { // 成功回调
					var code = resp.code;	
					callback(resp, postDataStr);
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
		,JWT_KEY:JWT_KEY
		,logout:function() {
			sessionStorage.clear()
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

