var GlobalConfig = (function () {
    var doSave = false;

    var $grid = $('#grid').datagrid({
        loader: function (param, success, error) {
            ApiUtil.post('global.config.pagelist', param, function (resp) {
                var respData = resp.data;
                success(respData);
            });
        }
        ,pagination :true
        ,idField:'id'
        ,columns : [[
            {field: 'keyName', title: '配置域', width: 150},
            {field: 'fieldName', title: '配置项', width: 200},
            {field: 'fieldValue', title: '配置值', width: 150},
            {field: 'remark', title: '描述', width: 300},
            {field : '_opt',title : '操作', width: 100,formatter:function(val,obj,index){
                var btns = [
                    '<a href="#" onclick="GlobalConfig.update(' + index + ')">修改</a>'
                ];
                return btns.join(' | ');
            }},
        ]]
    });

    $('#addBtn').click(function () {
        $('#frm').form('clear');
        $('#win').dialog('open');
        doSave = true;
    });

    $('#saveBtn').click(function () {
        var uri = doSave ? 'global.config.add' : 'global.config.update';
        var $form = $('#frm');
        var validateResult = $form.form('validate');
        if (validateResult) {
            var data = $form.form('getData');
            ApiUtil.post(uri, data, function (resp) {
                $('#win').dialog('close');
                $('#grid').datagrid('reload');
            });
        }
    })

    return {
        update:function (index) {
            var row = $grid.datagrid('getRow', index);
            $('#frm').form('load',row);
            $('#win').dialog('open');
            doSave = false;
        }
    }
})();