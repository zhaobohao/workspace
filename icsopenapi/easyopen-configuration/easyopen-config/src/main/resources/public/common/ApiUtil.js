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
var ApiUtil = (function(){	
	var params = {};
	var JWT_KEY = "easyconf_jwt";
	var ACCESS_TOKEN_KEY = "easyconf_access_token";

	var url = '../api';
	var app_key = 'test';
	var secret = '123456';
	
	sdk.config({
		url : url
	    ,app_key : app_key
	    ,secret : secret
	});
    
    (function() {
    	var aPairs, aTmp;  
        var queryString = window.location.search.toString();
        queryString = queryString.substr(1, queryString.length); //remove   "?"     
        aPairs = queryString.split("&");  
        for (var i = 0; i < aPairs.length; i++) {  
            aTmp = aPairs[i].split("=");  
            params[aTmp[0]] = decodeURIComponent(aTmp[1]);
        }  
    })();
    
    /* **************私有方法************** */
    function getJwt() {
    	return localStorage.getItem(JWT_KEY) || '';
    }

    function getAccessToken() {
        return localStorage.getItem(ACCESS_TOKEN_KEY) || '';
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

    function formatDate(time)
    {
        var y = time.getFullYear();
        var m = time.getMonth()+1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
    }
    /* ************************************* */
    
    return {    	
    	post:function(name,data,callback) {
    		sdk.post({
    			name   : name // 接口名
    			,data  : data // 请求参数
				,access_token : getAccessToken()
    			,callback:function(resp,postDataStr) { // 成功回调
    				var code = resp.code;
                	if(!code || code == '-9') {
                        MsgUtil.topMsg('系统错误');
                		return;
                	}
                	if(code == '-100' || code == '18' || code == '21') { // 未登录
                		ApiUtil.logout();
                		return;
                	}
                	if(code == '0') { // 成功
                		callback(resp,postDataStr);
                	} else {
                        MsgUtil.topMsg(resp.msg);
                	}
    			}
    		});
         }
    	,logout:function() {
            this.post('nologin.admin.logout',{},function (resp) {
            	ApiUtil.removeAccessToken();
                top.location = 'login.html?q=' + new Date().getTime();
            })
    	}
    	,setJwt:function(jwt) {
    		localStorage.setItem(JWT_KEY,jwt);
    	}
    	,removeJwt:function() {
    		localStorage.removeItem(JWT_KEY);
    	}
        ,setAccessToken:function (accessToken) {
            localStorage.setItem(ACCESS_TOKEN_KEY,accessToken);
        }
        ,removeAccessToken:function () {
            localStorage.removeItem(ACCESS_TOKEN_KEY);
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
    	,page:function(resId) {
    		window.location = 'main.html?resId=' + resId
    	}
    	,goMain:function() {
    		delete params.opt;
    		var q = $.param(params);
    		window.location = 'main.html?' + q;
    	}
    	,getUrl:function() {
    		return url;
    	}
    	,formatTime:function(time) {
    		return formatDate(new Date(time));
    	}
    };
})();