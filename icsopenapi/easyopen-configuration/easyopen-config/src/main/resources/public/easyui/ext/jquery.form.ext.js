/**
 * jquery easyui扩展
 */
 
$.extend($.fn.form.methods, {
	/**
	 * 获取表单数据,返回json
	 */
	getData: function($form, fieldName) {
		var data = {};
        var dataArr = $form.serializeArray();
        for (var i = 0, len = dataArr.length; i < len; i++) {
            var item = dataArr[i];
            var name = item.name;
            var itemValue = item.value;
            if(name && itemValue) {
	            var dataValue = data[name];
	
	            if (dataValue) {
	                if ($.isArray(dataValue)) {
	                    dataValue.push(itemValue);
	                } else {
	                    data[name] = [dataValue, itemValue];
	                }
	            } else {
	                data[name] = itemValue;
	            }
            }
        }
        
        if (typeof fieldName === 'string') {
            return data[fieldName];
        }
        
        return data;
    } 
});
