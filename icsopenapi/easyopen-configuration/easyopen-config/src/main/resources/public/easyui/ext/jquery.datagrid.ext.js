/**
 * jquery easyui扩展
 */
 
$.extend($.fn.datagrid.methods, {
	/**
	 * 获取表格行
	 * @param index 行索引,从0开始
	 */
	getRow: function($datagrid, index) {
		$datagrid.datagrid('selectRow',index);
		var row = $datagrid.datagrid('getSelected');
        return row;
    }
    /**
	 * 获取表格勾选的id
     * @param $datagrid
     * @param idName
     * @returns {Array}
     */
    ,getCheckedId:function ($datagrid, idName) {
    	var idField = $datagrid.datagrid('options').idField || 'id';
        idName = idName || idField;
		var rows = $datagrid.datagrid('getChecked') || [];
		if(rows.length == 0) {
			return rows;
		}
		var ret = [];
        for (var i = 0; i < rows.length; i++) {
            var obj = rows[i];
			ret.push(obj[idName]);
        }
        return ret;
    }
});
