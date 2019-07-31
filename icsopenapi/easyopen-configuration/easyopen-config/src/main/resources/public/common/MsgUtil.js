
var MsgUtil = {
	topMsg:function(msg,title){
		title = title || "提示";
		this.getJQ().messager.show({
			title: title,
			msg: msg,
			showSpeed:300,
			style:{
				right:'',
				top:document.body.scrollTop+document.documentElement.scrollTop,
				bottom:''
			}
		});
	}
	,alert:function(msg,title,type){
		title = title || "提示";
		type = type || 'info'
		
		if(msg && msg.length > 1000){
			this.getJQ().messager.show({
				title: title,
				msg: '<div style="overflow-y: auto; overflow-x:auto;">'+msg+'</div>',
				width:600,
				height:410,
				showType:null,
				timeout:0,
                modal:true,
                draggable:true,
				style:{
					right:'',
					bottom:''
				}
			});
		}else{
            this.getJQ().messager.alert(title,msg,type);
		}
		
	}
	,msg:function (msg,title,width) {
        title = title || "提示";
        this.getJQ().messager.show({
            title: title,
            msg: '<div style="overflow-y: auto; overflow-x:auto;">'+msg+'</div>',
            width:width || 600,
            showType:null,
            timeout:0,
            modal:true,
            draggable:true,
            style:{
                right:'',
                bottom:''
            }
        });
    }
	,error:function(msg,title){
		title = title || "错误";
		this.alert(msg,title,'error')
	}
	,confirm:function(msg,callback,title){
		title = title || "确认";
		this.getJQ().messager.confirm(title,msg,function(r){    
		    if (r){    
		        callback();
		    } 
		});
	}
	,getJQ:function(){
		return parent.$ || $;
	}
}  

	

