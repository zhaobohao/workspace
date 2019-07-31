var ApiConfig = (function () {
    function initApiGrid() {
        $('#apiGrid').datagrid({
            loader: function (param, success, error) {
                param.app = app;
                ApiUtil.post('app.api.pagelist', param, function (resp) {
                    var respData = resp.data;
                    success(respData);
                });
            },
            pageSize:20,
            pagination: true,
            singleSelect: true,
            toolbar: '#apiGridToolBar',
            columns: [[
                {field: 'name', title: '接口名', width: 150},
                {field: 'version', title: '版本号', width: 100},
                {field: 'description', title: '接口描述', width: 200},
                {
                    field: 'gmtCreate', title: '添加时间', width: 200, formatter: function (value, row, index) {
                    return ApiUtil.formatTime(value);
                }
                },
                {
                    field: 'status', title: '状态' + '<a href="#" onclick="ApiConfig.showStatus(); return false;">[？]</a>', width: 100, formatter: function (status, row, index) {
                    if (status == 0) {
                        return '<span class="green">使用中</span>';
                    } else if (status == 1) {
                        return '<span class="red">未使用</span>';
                    } else {
                        return '未知状态';
                    }
                }
                },
                {
                    field: 'hasAuthed', title: '授权状态', width: 100, formatter: function (hasAuthed, row, index) {
                    if (hasAuthed) {
                        return '<span class="green">已授权</span>';
                    } else {
                        return '<span class="red">未授权</span>';
                    }
                }
                },
                {
                    field: '_opt', title: '操作', width: 100, formatter: function (value, row, index) {
                    var btns = [
                        '<a href="#" onclick="ApiConfig.auth(' + index + ')">授权</a>'
                    ];
                    return btns.join(' | ');
                }
                }
            ]]
        });
    }

    function initRoleTree() {
        $('#apiRoleTree').tree({
            loader: function (param, success, error) {
                param.app = app;
                ApiUtil.post('role.tree.listall', param, function (resp) {
                    var respData = resp.data;
                    success(respData);
                });
            }
            , checkbox: true
            , onCheck: function (node, check) {
                var target = node.target;
                var $el = $(target);
                if (check) {
                    $el.find('.tree-title').addClass('tree-checked');
                } else {
                    $el.find('.tree-title').removeClass('tree-checked');
                }
            }
        });
    }

    function uncheckApiRoleTree() {
        var $tree = $('#apiRoleTree');
        $tree.find('.tree-title').removeClass('tree-checked');

        var rootNode = $tree.tree('find', 0);
        $tree.tree('uncheck', rootNode.target);
    }

    $('#searchApiBtn').click(function () {
        search();
    });

    $('#apiNameSch').on('keyup', function (e) {
        if (e.keyCode == 13) {
            search();
        }
    });

    $('#apiRoleSaveBtn').click(function () {
        var roleCodes = $('#apiRoleTree').tree('getCheckedId');	// get checked nodes
        var apiId = $('#apiRoleApiId').val();
        if(!apiId) {
            alert('apiId不能为空');
            return;
        }
        var data = {
            app:app,
            roleCodes:roleCodes,
            apiId:apiId
        };
        ApiUtil.post('api.role.update', data, function (resp) {
            $('#apiRoleWin').dialog('close');
            $('#apiGrid').datagrid('reload');
        });
    });

    function search() {
        var val = $('#apiNameSch').val();
        var data = {app: app};
        if (val) {
            data.name = val;
        }
        $('#apiGrid').datagrid('load', data);
    }

    return {
        init:function () {
            initApiGrid();
            initRoleTree();
        }
        ,auth: function (index) {
            var row = $('#apiGrid').datagrid('getRow', index)
            var apiId = row.id;
            $('#apiRoleApiId').val(apiId);
            uncheckApiRoleTree();
            var $tree = $('#apiRoleTree');
            // 获取接口已经授权的角色
            ApiUtil.post('api.role.listall', {apiId: apiId}, function (resp) {
                var roleApiList = resp.data;
                for (var i = 0; i < roleApiList.length; i++) {
                    var apiInfo = roleApiList[i];
                    var node = $tree.tree('find', apiInfo.roleCode);
                    if (node) {
                        $tree.tree('check', node.target);
                    }
                }
            });

            $('#apiRoleWin').dialog('setTitle', '接口授权' + '<span class="red">[' + row.name + ' ' + row.version + ']</span>').dialog('open');
        }
        ,showStatus:function () {
            var msg = '<span class="red">未使用</span>：应用端已经移除的接口。';
            MsgUtil.alert(msg, '状态说明');
        }
    }
})();