var PermissionConfig = (function () {
    function initRoleGrid() {
        $('#roleGrid').datagrid({
            loader: function (param, success, error) {
                ApiUtil.post('role.listall', param, function (resp) {
                    var respData = resp.data;
                    success(respData);
                });
            },
            singleSelect: true,
            title:'角色列表',
            columns: [[
                {field: 'roleCode', title: '角色码', width: 200},
                {field: 'description', title: '描述', width: 200},
                {
                    field: 'gmtCreate', title: '添加时间', width: 200, formatter: function (value, row, index) {
                    return ApiUtil.formatTime(value);
                }
                },
                {
                    field: '_opt', title: '操作', width: 150, formatter: function (value, row, index) {
                    var btns = [
                        '<a href="#" onclick="PermissionConfig.updateRole(' + index + ')">修改</a>'
                        ,'<a href="#" onclick="PermissionConfig.setRoleApi(' + index + ')">接口权限</a>'
                    ];
                    return btns.join(' | ');
                }
                }
            ]]
        });
    }

    var treeInited = false;
    var treeInitCallback;
    function intiApiTree(callback) {
        treeInitCallback = callback;
        var $tree = $('#apiTree');
        if (!treeInited) {
            $tree.tree({
                loader: function (param, success, error) {
                    param.app = app;
                    ApiUtil.post('app.api.tree.listall', param, function (resp) {
                        var respData = resp.data;
                        success(respData);
                        treeInitCallback && treeInitCallback($tree);
                    });
                }
                ,checkbox:true
            });
            treeInited = true;
        } else {
            $tree.tree('reload');
        }
    }

    function loadRoleFrm(row,uri) {
        $('#roleFrm').attr('uri',uri).form('clear').form('load', row);
        $('#roleWin').dialog('open');
    }
    
    function addRole() {
        $('#roleCode').textbox('readonly', false);
        loadRoleFrm({},'role.add');
	}
    
   	$('#addRoleBtn').click(function () {
        addRole();
    });
    
    $('#roleSaveBtn').click(function(){
    	$('#roleFrm').form('submit', {
            onSubmit: function () {
                var uri = $(this).attr('uri');
                var validateResult = $(this).form('validate');
                if (validateResult) {
                    var data = $(this).form('getData');
                    data.app = app;
                    ApiUtil.post(uri, data, function (resp) {
                        $('#roleWin').dialog('close');
                        $('#roleGrid').datagrid('reload');
                    });
                }
                return false;
            }
        });
    });

    $('#apiSaveBtn').click(function () {
        var apiIds = $('#apiTree').tree('getCheckedId');	// get checked nodes
        var roleCode = $('#apiRoleCode').val();
        if(!roleCode) {
            MsgUtil.topMsg('roleCode不能为空');
            return;
        }
        var data = {
            app:app,
            apiIds:apiIds,
            roleCode:roleCode
        };
        ApiUtil.post('role.api.update', data, function (resp) {
            $('#apiWin').dialog('close');
        });
    });

    // public函数
	return {
        init:function () {
            initRoleGrid();
        }
        ,updateRole:function (index) {
            var row = $('#roleGrid').datagrid('getRow', index);
            $('#roleCode').textbox('readonly', true);
            loadRoleFrm(row, 'role.update');
        }
        ,setRoleApi:function (index) {
            var row = $('#roleGrid').datagrid('getRow', index);
            $('#apiRoleCode').val(row.roleCode);

            intiApiTree(function($tree) {
                // 获取角色已经拥有的接口
                ApiUtil.post('role.api.list', row, function (resp) {
                    var roleApiList = resp.data;
                    for(var i=0; i<roleApiList.length; i++) {
                        var apiInfo = roleApiList[i];
                        var node = $tree.tree('find', apiInfo.apiId);
                        if(node) {
                            $tree.tree('check', node.target);
                        }
                    }
                });
            });

			$('#apiWin').dialog('setTitle', '接口信息' + '<span class="red">[' + row.description + ']</span>').dialog('open');
        }
	} // end return;
})();


